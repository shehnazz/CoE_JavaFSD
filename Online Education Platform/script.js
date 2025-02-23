// Course Search Functionality
function searchCourses() {
    let input = document.getElementById("search").value.toLowerCase();
    let courses = document.querySelectorAll(".course-card");

    courses.forEach(course => {
        let text = course.textContent.toLowerCase();
        course.style.display = text.includes(input) ? "block" : "none";
    });
}

// Form Validation
document.addEventListener("DOMContentLoaded", function() {
    const enrollForm = document.getElementById("enroll-form");

    if (enrollForm) {
        enrollForm.addEventListener("submit", function(event) {
            let name = enrollForm.querySelector("input[name='name']").value.trim();
            let email = enrollForm.querySelector("input[name='email']").value.trim();

            if (name === "" || email === "") {
                alert("Please fill in all required fields.");
                event.preventDefault();
            } else {
                alert("Enrollment Successful!");
            }
        });
    }
});
