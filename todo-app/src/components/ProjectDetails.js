import axios from 'axios';
import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';

function ProjectDetail() {
    const { projectId } = useParams();
    const [project, setProject] = useState(null);
    const [newTodo, setNewTodo] = useState('');
    const [editTitle, setEditTitle] = useState('');
    const [editingTodo, setEditingTodo] = useState(null);
    const [editTodoDescription, setEditTodoDescription] = useState('');
    const [editStatus, setEditStatus] = useState('');
    const [projectTitleUpdated,setProjectTitleUpdated] = useState(false);

    useEffect(() => {
        const fetchProject = async () => {
            try {
                const response = await axios.get(`api/projects/${projectId}`);
                setProject(response.data);
                setEditTitle(response.data.title);
            } catch (error) {
                console.error('Failed to fetch project:', error);
            }
        };

        fetchProject();
    }, [projectId]);

    const handleAddTodo = async () => {
        try {
            const response = await axios.post(`api/todos/project/${projectId}`, null, { params: { description: newTodo } });
            setProject((prevProject) => ({
                ...prevProject,
                todos: [...prevProject.todos, response.data],
            }));
            setNewTodo('');
        } catch (error) {
            console.error('Failed to add todo:', error);
        }
    };

    const handleUpdateTodo = async (todoId, updatedFields) => {
        try {
            const response = await axios.put(`api/todos/${todoId}`, null, { params: updatedFields });
            setProject((prevProject) => ({
                ...prevProject,
                todos: prevProject.todos.map((todo) => (todo.id === todoId ? response.data : todo)),
            }));
            setEditingTodo(null); 
        } catch (error) {
            console.error('Failed to update todo:', error);
        }
    };

    const handleDeleteTodo = async (todoId) => {
        try {
            await axios.delete(`api/todos/${todoId}`);
            setProject((prevProject) => ({
                ...prevProject,
                todos: prevProject.todos.filter((todo) => todo.id !== todoId),
            }));
        } catch (error) {
            console.error('Failed to delete todo:', error);
        }
    };

    const handleUpdateTitle = async () => {
        try {
            const response = await axios.put(`api/projects/${projectId}`, null, { params: { newTitle: editTitle } });
            setProjectTitleUpdated(true);
            setProject(response.data);
        } catch (error) {
            console.error('Failed to update project title:', error);
        }
    };

    const handleDownloadSummary = async () => {
        try {
            const response = await axios.get(`api/projects/${projectId}/export`, {
                responseType: 'blob',
            });
            const url = window.URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', `${project.title}.md`);
            document.body.appendChild(link);
            link.click();
            link.remove();
        } catch (error) {
            console.error('Failed to download summary:', error);
        }
    };

    const startEditingTodo = (todo) => {
        setEditingTodo(todo.id);
        setEditTodoDescription(todo.description);
    };

    const cancelEditingTodo = () => {
        setEditingTodo(null);
        setEditTodoDescription('');
    };

    if (!project) {
        return <div>Loading...</div>;
    }

    const incompleteTodos = project.todos.filter((todo) => todo.status !== 'complete');
    const completeTodos = project.todos.filter((todo) => todo.status === 'complete');

    return (
        <div className="container mx-auto p-4">
            <div className="mb-2">
                <input
                    type="text"
                    value={editTitle}
                    onChange={(e) => setEditTitle(e.target.value)}
                    className="border p-2 w-full"
                />
                <button
                    onClick={handleUpdateTitle}
                    className="bg-blue-500 text-white p-2 mt-2"
                >
                    Update Title
                </button>
                <button
                    onClick={handleDownloadSummary}
                    className="bg-purple-500 text-white p-2 mt-2 ml-2"
                >
                    Download Summary
                </button>
            </div>
            {   
                projectTitleUpdated && 
                <div className='mb-2'>
                    <span className='text-blue-500'>Title Updated</span>
                </div>
            }

            <div className="mb-4">
                <input
                    type="text"
                    value={newTodo}
                    onChange={(e) => setNewTodo(e.target.value)}
                    placeholder="New Todo"
                    className="border p-2 w-full"
                />
                <button
                    onClick={handleAddTodo}
                    className="bg-green-500 text-white p-2 mt-2"
                >
                    Add Todo
                </button>
            </div>

            <ul>
                {incompleteTodos.map((todo) => (
                    <li key={todo.id} className="border p-2 mb-2 flex justify-between items-center">
                        {editingTodo === todo.id ? (
                            <div className="flex-grow">
                                <input
                                    type="text"
                                    value={editTodoDescription}
                                    onChange={(e) => setEditTodoDescription(e.target.value)}
                                    className="border p-2 w-full"
                                />
                            </div>
                        ) : (
                            <div>
                                <p>{todo.description}</p>
                                <p className="text-sm text-gray-500">{new Date(todo.createdDate).toLocaleDateString()}</p>
                            </div>
                        )}

                        <div className="flex items-center">
                            {editingTodo === todo.id ? (
                                <>
                                    <button
                                        onClick={() => handleUpdateTodo(todo.id, { description: editTodoDescription, status: editStatus })}
                                        className="bg-blue-500 text-white p-2 ml-2"
                                    >
                                        Save
                                    </button>
                                    <button
                                        onClick={cancelEditingTodo}
                                        className="bg-gray-500 text-white p-2 ml-2"
                                    >
                                        Cancel
                                    </button>
                                </>
                            ) : (
                                <>
                                    <button
                                        onClick={() => startEditingTodo(todo)}
                                        className="bg-yellow-500 text-white p-2"
                                    >
                                        Edit
                                    </button>
                                    <button
                                        onClick={() => handleUpdateTodo(todo.id, {description: todo.description ,status: todo.status === 'complete' ? 'pending' : 'complete' })}
                                        className={`p-2 ${todo.status === 'complete' ? 'bg-yellow-500' : 'bg-blue-500'} text-white ml-2`}
                                    >
                                        {todo.status === 'complete' ? 'Mark as Pending' : 'Mark as Complete'}
                                    </button>
                                    <button
                                        onClick={() => handleDeleteTodo(todo.id)}
                                        className="bg-red-500 text-white p-2 ml-2"
                                    >
                                        Delete
                                    </button>
                                </>
                            )}
                        </div>
                    </li>
                ))}
            </ul>

            <h2 className="text-lg font-bold mt-4">Completed</h2>
            <ul>
                {completeTodos.map((todo) => (
                    <li key={todo.id} className="border p-2 mb-2 flex justify-between items-center">
                        <div>
                            <p>{todo.description}</p>
                            <p className="text-sm text-gray-500">{new Date(todo.createdDate).toLocaleDateString()}</p>
                        </div>
                        <button
                            onClick={() => handleUpdateTodo(todo.id, {description: todo.description, status: 'pending' })}
                            className="bg-yellow-500 text-white p-2"
                        >
                            Mark as Pending
                        </button>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default ProjectDetail;
