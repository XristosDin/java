const patientbutton = document.getElementById("patientbutton");
const doctorbutton = document.getElementById("doctorbutton");
const adminbutton = document.getElementById("adminbutton");


function goToPatientPage() {
    window.location.href = "../Patient/PatientLoginRegister.html";
}

function goToDoctorPage() {
    window.location.href = "../Doctor/DoctorLogin.html";
}

function goToAdminPage() {
    window.location.href = "../Admin/Admin.html";
}

patientbutton.addEventListener("click", goToPatientPage);
doctorbutton.addEventListener("click", goToDoctorPage);
adminbutton.addEventListener("click", goToAdminPage);
