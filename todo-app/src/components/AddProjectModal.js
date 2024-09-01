import React from 'react';

function AddProjectModal({ isOpen, onClose, onSubmit, children }) {
    if (!isOpen) return null;

    return (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center">
            <div className="bg-white p-6 rounded-lg shadow-lg w-1/3">
                <div className="flex justify-between items-center">
                    <h2 className="text-2xl font-bold">New Project</h2>
                    <button onClick={onClose} className="text-gray-600 hover:text-gray-900">&times;</button>
                </div>
                <div className="mt-4">
                    {children}
                </div>
                <div className="mt-6 flex justify-end">
                    <button onClick={onClose} className="bg-gray-300 text-gray-800 py-2 px-4 rounded-md mr-2">
                        Cancel
                    </button>
                    <button onClick={onSubmit} className="bg-blue-500 text-white py-2 px-4 rounded-md">
                        Add Project
                    </button>
                </div>
            </div>
        </div>
    );
}

export default AddProjectModal;
