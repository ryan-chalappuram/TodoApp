import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import AddProjectModal from './AddProjectModal';

function ProjectList() {
    const [projects, setProjects] = useState([]);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [newProjectTitle, setNewProjectTitle] = useState('');

    useEffect(() => {
        const fetchProjects = async () => {
            try {
                const response = await axios.get('/api/projects');
                setProjects(response.data);
            } catch (error) {
                console.error('Error fetching projects', error);
            }
        };

        fetchProjects();
    }, []);

    const handleAddProject = async () => {
        try {
            const response = await axios.post('/api/projects', null,{ params:{title: newProjectTitle }});
            setProjects((prevProjects) => [...prevProjects, response.data]);
            setNewProjectTitle('');
            setIsModalOpen(false);
        } catch (error) {
            console.error('Error adding project', error);
        }
    };

    return (
        <div className="p-8 bg-gray-100 min-h-screen">
            <h2 className="text-3xl font-bold mb-6">Projects</h2>
            <ul className="space-y-4">
                {projects.map((project) => (
                    <li key={project.id} className="bg-white p-4 rounded-lg shadow-md">
                        <Link to={`/projects/${project.id}`} className="text-blue-500">
                            {project.title}
                        </Link>
                    </li>
                ))}
            </ul>
            <button
                onClick={() => setIsModalOpen(true)}
                className="mt-6 inline-block bg-blue-500 text-white py-2 px-4 rounded-md"
            >
                Create New Project
            </button>

            <AddProjectModal
                isOpen={isModalOpen}
                onClose={() => setIsModalOpen(false)}
                onSubmit={handleAddProject}
            >
                <input
                    type="text"
                    value={newProjectTitle}
                    onChange={(e) => setNewProjectTitle(e.target.value)}
                    placeholder="Project Title"
                    className="border p-2 w-full"
                />
            </AddProjectModal>
        </div>
    );
}

export default ProjectList;
