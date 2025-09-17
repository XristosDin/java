document.addEventListener("DOMContentLoaded", () => {
    // Check if user is logged in
    const username = localStorage.getItem('patientLoggedIn');
    const userType = localStorage.getItem('userType');

    // If not logged in or not a patient, redirect to login page
    if (!username || userType !== 'patient') {
        window.location.href = 'PatientLoginRegister.html';
        return;
    }

    // Add logout button to the page
    const header = document.querySelector('header');
    if (header) {
        const logoutBtn = document.createElement('button');
        logoutBtn.textContent = 'Logout';
        logoutBtn.className = 'logout-btn';
        logoutBtn.onclick = function() {
            localStorage.removeItem('patientLoggedIn');
            localStorage.removeItem('userType');
            localStorage.removeItem('loginTime');
            window.location.href = 'PatientLoginRegister.html';
        };
        header.appendChild(logoutBtn);
    }

    // Update welcome message with username
    const welcomeText = document.getElementById("welcomeText");
    if (welcomeText) {
        welcomeText.textContent = `Welcome, ${username}!`;
    }

    const appointmentTable = document.getElementById("availableAppointmentsTable").getElementsByTagName("tbody")[0];
    const specialitySelect = document.getElementById("specialitySelect");

    async function loadAvailableAppointments() {
        const speciality = specialitySelect.value;
        const response = await fetch(`http://localhost:8080/appointments/speciality/${speciality}/available`);
        const appointments = await response.json();

        appointmentTable.innerHTML = "";

        if (appointments.length === 0) {
            appointmentTable.innerHTML = `<tr><td colspan="4">No available appointments found.</td></tr>`;
            return;
        }

        appointments.forEach(appt => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${appt.doctorUserName}</td>
                <td>${new Date(appt.dayTime).toLocaleString()}</td>
                <td>${appt.fee}</td>
                <td><button onclick="bookAppointment(${appt.id})">Book</button></td>
            `;
            appointmentTable.appendChild(row);
        });
    }

    window.bookAppointment = async function (appointmentId) {
        // Get username from localStorage
        const patientUsername = localStorage.getItem('patientLoggedIn');

        // Check if still logged in
        if (!patientUsername) {
            alert("You must be logged in to book an appointment.");
            window.location.href = 'PatientLoginRegister.html';
            return;
        }

        // First get the appointment details
        const getResponse = await fetch(`http://localhost:8080/appointments/${appointmentId}`);
        if (!getResponse.ok) {
            alert("Failed to retrieve appointment details.");
            return;
        }

        const appointment = await getResponse.json();

        // Update the appointment with patient username and set available to false
        const updateResponse = await fetch(`http://localhost:8080/appointments`, {
            method: "PUT",
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                id: appointment.id,
                doctorUserName: appointment.doctorUserName,
                doctorSpeciality: appointment.doctorSpeciality,
                patientUserName: patientUsername,
                dayTime: appointment.dayTime,
                fee: appointment.fee,
                available: false
            })
        });

        if (updateResponse.ok) {
            alert("Appointment booked!");

            loadAvailableAppointments();
            loadMyAppointments(); // Reload my appointments as well
        } else {
            alert("Failed to book appointment.");
        }
    }

    window.cancelAppointment = async function(appointmentId) {
        // First get the appointment details
        const getResponse = await fetch(`http://localhost:8080/appointments/${appointmentId}`);
        if (!getResponse.ok) {
            alert("Failed to retrieve appointment details.");
            return;
        }

        const appointment = await getResponse.json();

        // Update the appointment to set it back to available and remove patient
        const updateResponse = await fetch(`http://localhost:8080/appointments`, {
            method: "PUT",
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                id: appointment.id,
                doctorUserName: appointment.doctorUserName,
                doctorSpeciality: appointment.doctorSpeciality,
                patientUserName: null, // Remove patient
                dayTime: appointment.dayTime,
                fee: appointment.fee,
                available: true // Set back to available
            })
        });

        if (updateResponse.ok) {
            alert("Appointment cancelled.");
            loadMyAppointments();
            loadAvailableAppointments(); // Reload available appointments as well
        } else {
            alert("Cancellation failed.");
        }
    }

    async function loadMyAppointments() {
        const patientUsername = localStorage.getItem('patientLoggedIn');

        // Check if still logged in
        if (!patientUsername) {
            alert("Session expired. Please log in again.");
            window.location.href = 'PatientLoginRegister.html';
            return;
        }

        const response = await fetch(`http://localhost:8080/appointments/patient/${patientUsername}`);
        const appointments = await response.json();

        const myTable = document.getElementById("myAppointmentsTable").getElementsByTagName("tbody")[0];
        myTable.innerHTML = "";

        if (appointments.length === 0) {
            myTable.innerHTML = `<tr><td colspan="4">No appointments found.</td></tr>`;
            return;
        }

        appointments.forEach(appt => {
            const apptDate = new Date(appt.dayTime);
            const nowPlus3Days = new Date();
            nowPlus3Days.setDate(nowPlus3Days.getDate() + 3);

            const canCancel = apptDate >= nowPlus3Days;

            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${appt.doctorUserName}</td>
                <td>${appt.doctorSpeciality}</td>
                <td>${apptDate.toLocaleString()}</td>
                <td>
                    ${canCancel ? `<button onclick="cancelAppointment(${appt.id})">Cancel</button>` : "-"}
                </td>
            `;
            myTable.appendChild(row);
        });
    }

    document.getElementById("searchAppointments").addEventListener("click", loadAvailableAppointments);

    // Load both appointment lists when the page loads
    loadMyAppointments();
    loadAvailableAppointments();
});