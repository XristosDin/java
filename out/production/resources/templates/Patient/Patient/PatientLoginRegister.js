document.addEventListener('DOMContentLoaded', () => {
    // Check if user is already logged in
    const loggedInUser = localStorage.getItem('patientLoggedIn');
    if (loggedInUser) {
        window.location.href = 'PatientHome.html';
        return;
    }

    // Ensure form exists before adding event listener
    const form = document.getElementById('registerForm');
    if (!form) {
        console.error('Form with ID "registerForm" not found.');
        return;
    }

    form.addEventListener('submit', async (event) => {
        event.preventDefault(); // Prevents page reload on form submit

        const patientData = {
            username: document.getElementById('registerUsername').value,
            password: document.getElementById('registerPassword').value,
            firstName: document.getElementById('registerFirstName').value,
            lastName: document.getElementById('registerLastName').value,
            AMKA: document.getElementById('registerAmka').value
        };

        console.log(patientData);

        try {
            const response = await fetch('http://localhost:8080/patients', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(patientData)
            });

            if (response.ok) {
                alert('Patient registered successfully!');
                // Store user info in localStorage
                localStorage.setItem('patientLoggedIn', patientData.username);
                localStorage.setItem('userType', 'patient');
                localStorage.setItem('loginTime', new Date().toString());
                // Redirect to PatientHome
                window.location.href = 'PatientHome.html';
            } else {
                alert('Error registering patient. Please try again.');
            }
        } catch (error) {
            console.error('Error:', error);
            alert('Request failed. Please check your network connection.');
        }
    });
    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', async (event) => {
            event.preventDefault();

            const username = document.getElementById('loginUsername').value;
            const password = document.getElementById('loginPassword').value;

            if (!username || !password) {
                alert('Please enter username and password.');
                return;
            }

            const loginData = {username, password};

            console.log('Logging in:', loginData);

            try {
                const response = await fetch('http://localhost:8080/patients/login', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify(loginData)
                });

                const result = await response.text();

                if (result.includes("Login successful")) {
                    // Store user info in localStorage
                    localStorage.setItem('patientLoggedIn', username);
                    localStorage.setItem('userType', 'patient');
                    localStorage.setItem('loginTime', new Date().toString());
                    window.location.href = 'PatientHome.html';
                } else {
                    alert('Invalid username or password.');
                }
            } catch (error) {
                console.error('Error:', error);
                alert('Login failed.');
            }
        });
    }
});
