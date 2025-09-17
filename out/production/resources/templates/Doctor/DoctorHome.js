document.addEventListener("DOMContentLoaded", () => {
    // Check if user is logged in
    const username = localStorage.getItem('doctorLoggedIn');
    const userType = localStorage.getItem('userType');

    // If not logged in or not a doctor, redirect to login page
    if (!username || userType !== 'doctor') {
        window.location.href = 'DoctorLogin.html';
        return;
    }

    // Add logout button to the page
    const header = document.querySelector('header');
    if (header) {
        const logoutBtn = document.createElement('button');
        logoutBtn.textContent = 'Logout';
        logoutBtn.className = 'logout-btn';
        logoutBtn.onclick = function() {
            localStorage.removeItem('doctorLoggedIn');
            localStorage.removeItem('userType');
            localStorage.removeItem('loginTime');
            window.location.href = 'DoctorLogin.html';
        };
        header.appendChild(logoutBtn);
    }

    // Variable to store doctor's speciality
    let doctorSpeciality = "";

    // Function to retrieve doctor data by username
    async function getDoctorData(username) {
        try {
            const response = await fetch(`http://localhost:8080/doctors/${username}`);
            if (response.ok) {
                const doctorData = await response.json();
                doctorSpeciality = doctorData.doctorSpeciality;
                return doctorData;
            } else {
                console.error("Failed to retrieve doctor data");
                return null;
            }
        } catch (error) {
            console.error("Error retrieving doctor data:", error);
            return null;
        }
    }

    // Update the welcome text with the username
    const welcomeText = document.getElementById("welcomeText");
    if (welcomeText) {
        welcomeText.textContent = `Welcome, Dr. ${username}!`;
    }

    // Keep this for form submission
    const doctorUsernameField = document.getElementById("doctorUserName");
    if (doctorUsernameField) {
        doctorUsernameField.value = username;
    }

    // Retrieve doctor data immediately and await it
    (async () => {
        const doctorData = await getDoctorData(username);
        if (doctorData) {
            console.log("Doctor speciality loaded:", doctorSpeciality);
        }
    })();

    const form = document.getElementById("appointmentForm");
    form.addEventListener("submit", async (event) => {
        event.preventDefault(); // Prevents page reload on form submit

        // Get username from localStorage
        const doctorUsername = localStorage.getItem('doctorLoggedIn');

        // Capture form values
        const date = document.getElementById("date").value;
        const time = document.getElementById("time").value;
        const feeValue = document.getElementById("fee").value;

        // Using the doctorSpeciality from the getDoctorData function
        // If it's still empty, try to get it again
        if (!doctorSpeciality) {
            await getDoctorData(doctorUsername);
        }

        // Combine Date & Time into an ISO 8601 timestamp
        const timestamp = new Date(`${date}T${time}:00`).toISOString();

        // Create the data object to send to the backend
        const appointmentData = {
            id: 0,
            doctorUserName: doctorUsername,
            patientUserName: null,
            doctorSpeciality: doctorSpeciality,
            dayTime: timestamp,
            fee: feeValue,
            available: true
        };

        console.log("Sending JSON:", appointmentData);

        // Validate required fields
        if (!doctorUsername || !date || !time) {
            alert("Please fill in all required fields.");
            return;
        }

        // Submit the appointment to the server
        try {
            const response = await fetch("http://localhost:8080/appointments", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(appointmentData)
            });

            if (response.ok) {
                document.getElementById("responseMessage").textContent = "Appointment created successfully!";
                loadAppointments();
            } else {
                document.getElementById("responseMessage").textContent = "Error creating appointment. Please try again.";
            }
        } catch (error) {
            console.error("Error:", error);
            document.getElementById("responseMessage").textContent = "Request failed. Please check your network connection.";
        }
    });

    // Add event listener for the filter dropdown (All / By Day / By Week)
    document.getElementById("filterType").addEventListener("change", () => {
        loadAppointments();
    });

    // Load and display appointments with filtering
    async function loadAppointments() {
        const doctorUsername = localStorage.getItem('doctorLoggedIn');
        const response = await fetch(`http://localhost:8080/appointments/doctor/${doctorUsername}`);
        const appointments = await response.json();

        const tableBody = document.querySelector("#appointmentsTable tbody");
        tableBody.innerHTML = "";

        // Filtering appointments by selected type
        const filterType = document.getElementById("filterType").value;
        const now = new Date();

        const filteredAppointments = appointments.filter(appt => {
            const apptDate = new Date(appt.dayTime);
            if (filterType === "day") {
                return apptDate.toDateString() === now.toDateString();
            } else if (filterType === "week") {
                const weekStart = new Date(now);
                weekStart.setDate(now.getDate() - now.getDay());
                const weekEnd = new Date(weekStart);
                weekEnd.setDate(weekEnd.getDate() + 6);
                return apptDate >= weekStart && apptDate <= weekEnd;
            }
            return true;
        });

        if (filteredAppointments.length === 0) {
            const row = document.createElement("tr");
            row.innerHTML = `<td colspan="6">No appointments found.</td>`;
            tableBody.appendChild(row);
            return;
        }

        // Render filtered appointments in the table
        filteredAppointments.forEach(appt => {
            const row = document.createElement("tr");
            const cancelBtn = document.createElement("button");
            cancelBtn.className = "cancel-btn";
            cancelBtn.textContent = "Cancel Appointment";
            cancelBtn.addEventListener("click", () => cancelAppointment(appt.id));

            const apptDate = new Date(appt.dayTime);

            row.innerHTML = `
            <td>${appt.patientUserName || "-"}</td>
            <td>${apptDate.toLocaleDateString()}</td>
            <td>${apptDate.toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'})}</td>
            <td>${appt.fee || "-"}</td>
            <td>${appt.available ? "Yes" : "No"}</td>
            <td></td>
            `;
            row.querySelector("td:last-child").appendChild(cancelBtn);
            tableBody.appendChild(row);
        });
    }

    // Cancel appointment function (delete request)
    async function cancelAppointment(appointmentId) {
        const response = await fetch(`http://localhost:8080/appointments/${appointmentId}`, {
            method: "DELETE"
        });

        if (response.ok) {
            loadAppointments();
        } else {
            alert("Failed to cancel appointment.");
        }
    }

    loadAppointments();
});
