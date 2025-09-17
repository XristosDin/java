document.addEventListener("DOMContentLoaded", () => {
    // Check if admin is already logged in
    const isAdminLoggedIn = localStorage.getItem('adminLoggedIn');
    const adminAuthDiv = document.getElementById("adminAuth");
    const doctorForm = document.getElementById("doctorForm");

    // If admin is already logged in, show the doctor form
    if (isAdminLoggedIn === 'true' && adminAuthDiv && doctorForm) {
        adminAuthDiv.style.display = "none";
        doctorForm.style.display = "block";

        // Add logout button
        const adminContainer = document.querySelector('.admin-container');
        if (adminContainer) {
            const logoutBtn = document.createElement('button');
            logoutBtn.textContent = 'Logout';
            logoutBtn.className = 'logout-btn';
            logoutBtn.style.marginTop = '10px';
            logoutBtn.onclick = function() {
                localStorage.removeItem('adminLoggedIn');
                window.location.reload();
            };
            adminContainer.appendChild(logoutBtn);
        }
    }

    // Admin authentication
    const authBtn = document.getElementById("authBtn");
    const adminPasswordInput = document.getElementById("adminPassword");

    if (authBtn && adminPasswordInput && doctorForm && adminAuthDiv) {
        // Function to handle authentication
        const authenticateAdmin = () => {
            const adminPassword = adminPasswordInput.value;
            if (adminPassword === "2004") {
                // Store admin login status in localStorage
                localStorage.setItem('adminLoggedIn', 'true');

                // Show the doctor form and hide the auth div
                adminAuthDiv.style.display = "none";
                doctorForm.style.display = "block";

                // Add logout button
                const adminContainer = document.querySelector('.admin-container');
                if (adminContainer) {
                    const logoutBtn = document.createElement('button');
                    logoutBtn.textContent = 'Logout';
                    logoutBtn.className = 'logout-btn';
                    logoutBtn.style.marginTop = '10px';
                    logoutBtn.onclick = function() {
                        localStorage.removeItem('adminLoggedIn');
                        window.location.reload();
                    };
                    adminContainer.appendChild(logoutBtn);
                }
            } else {
                alert("Incorrect admin password. Please try again.");
            }
        };

        // Add click event listener to the button
        authBtn.addEventListener("click", authenticateAdmin);

        // Add keypress event listener to the input field
        adminPasswordInput.addEventListener("keypress", (event) => {
            if (event.key === "Enter") {
                event.preventDefault(); // Prevent form submission
                authenticateAdmin();
            }
        });
    }

    // Ensure form exists before adding event listener
    const form = document.getElementById("doctorForm");
    if (!form) {
        console.error("Form with ID 'doctorForm' not found.");
        return;
    }

    form.addEventListener("submit", async (event) => {
        event.preventDefault(); // Prevents page reload on form submit

        const Adminjson = {
            firstName: document.getElementById("Name").value,
            lastName: document.getElementById("lastname").value,
            username: document.getElementById("username").value,
            password: document.getElementById("password").value,
            doctorSpeciality: document.getElementById("Speciality").value
        };

        console.log(Adminjson);

        try {
            const response = await fetch("http://localhost:8080/doctors", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(Adminjson)
            });

            if (response.ok) {
                alert("Doctor created successfully!");
            } else {
                alert("Error creating doctor. Please try again.");
            }
        } catch (error) {
            console.error("Error:", error);
            alert("Request failed. Please check your network connection.");
        }
    });
});
