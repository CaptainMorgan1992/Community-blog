import { useContext, useState } from 'react';
import GlobalContext from '../GlobalContext';

const RegisterPage = () => {
    const { registerUser } = useContext(GlobalContext);
    const [formData, setFormData] = useState({
        name: '',
        username: '',
        password: '',
        email: '',
    });
    const [registrationStatus, setRegistrationStatus] = useState(null);
    const [validationErrors, setValidationErrors] = useState([]);

    const validateFormData = () => {
        const errors = [];

        if (formData.username.length < 5) {
            errors.push('Username must be at least 5 characters.');
        }

        if (!/^\S+@\S+\.\S+$/.test(formData.email)) {
            errors.push('Please enter a valid email address.');
        }

        if (!/(?=.*\d)(?=.*[A-Z]).{8,}/.test(formData.password)) {
            errors.push('Password must be at least 8 characters and include 1 uppercase letter and 1 number.');
        }

        if (formData.name.length < 2) {
            errors.push('Name must be at least 2 characters.');
        }

        return errors;
    };

    const handleRegister = async () => {
        const validationErrors = validateFormData();

        if (validationErrors.length > 0) {
            setValidationErrors(validationErrors);
            return;
        }

        try {
            const response = await registerUser(formData);

            if (response.status === 200) {
                // Assuming the backend sends a plain string response
                const result = await response.text();
                setRegistrationStatus({ success: true, message: result });
            } else {
                // Handle non-OK responses (e.g., validation errors)
                const errorResponse = await response.json();
                console.error('Error registering user:', errorResponse.message);
            }
        } catch (error) {
            console.error('Error registering user:', error);
        }
    };


    return (
        <div className="register-container">
            <h2>Register Page</h2>
            {registrationStatus && registrationStatus.success ? (
                <div>
                    <p>Registration successful! Welcome, {registrationStatus.username}!</p>
                    <button type="button" onClick={() => setRegistrationStatus(null)}>
                        Login
                    </button>
                </div>
            ) : (
                <form>
                    <label>Name:</label>
                    <input type="text" name="name" value={formData.name} onChange={(e) => setFormData({ ...formData, name: e.target.value })} />

                    <label>Username:</label>
                    <input type="text" name="username" value={formData.username} onChange={(e) => setFormData({ ...formData, username: e.target.value })} />

                    <label>Password:</label>
                    <input type="password" name="password" value={formData.password} onChange={(e) => setFormData({ ...formData, password: e.target.value })} />

                    <label>Email:</label>
                    <input type="email" name="email" value={formData.email} onChange={(e) => setFormData({ ...formData, email: e.target.value })} />

                    {validationErrors.length > 0 && (
                        <div>
                            <p>Please fix the following errors:</p>
                            <ul>
                                {validationErrors.map((error, index) => (
                                    <li key={index}>{error}</li>
                                ))}
                            </ul>
                        </div>
                    )}

                    <button type="button" onClick={handleRegister}>
                        Register
                    </button>
                </form>
            )}
        </div>
    );
};

export default RegisterPage;
