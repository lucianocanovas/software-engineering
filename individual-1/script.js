const form = document.querySelector(".form");
const nameInput = document.querySelector('input[name="name"]');
const jobInput = document.querySelector('input[name="job"]');
const list = document.querySelector(".list-items");
const storageKey = "employees";
let employees = JSON.parse(localStorage.getItem(storageKey) || "[]");
let editingId = null;

function saveEmployees() {
    localStorage.setItem(storageKey, JSON.stringify(employees));
}

function updateList() {
    list.innerHTML = "";

    employees.forEach((employee) => {
        const listItem = document.createElement("li");
        listItem.className = "list-item";
        listItem.textContent = `${employee.name} - ${employee.job}`;

        const buttonsContainer = document.createElement("div");
        buttonsContainer.className = "buttons-container";
        listItem.appendChild(buttonsContainer);

        const editButton = document.createElement("button");
        editButton.className = "edit-button";
        editButton.type = "button";
        editButton.textContent = "Editar";
        editButton.addEventListener("click", () => startEditing(employee));

        const deleteButton = document.createElement("button");
        deleteButton.className = "delete-button";
        deleteButton.type = "button";
        deleteButton.textContent = "Eliminar";
        deleteButton.addEventListener("click", () => {
            employees = employees.filter((item) => item.id !== employee.id);
            saveEmployees();
            updateList();
        });

        buttonsContainer.appendChild(editButton);
        buttonsContainer.appendChild(deleteButton);
        list.appendChild(listItem);
    });
}

function startEditing(employee) {
    editingId = employee.id;
    nameInput.value = employee.name;
    jobInput.value = employee.job;
    form.querySelector("button[type='submit']").textContent = "Guardar cambios";
    nameInput.focus();
}

form.addEventListener("submit", (event) => {
    event.preventDefault();
    const name = nameInput.value.trim();
    const job = jobInput.value.trim();
    if (!name || !job) return;

    if (editingId) {
        employees = employees.map((employee) => employee.id === editingId
            ? { ...employee, name, job }
            : employee);
        editingId = null;
    } else {
        employees.push({ id: Date.now(), name, job });
    }

    saveEmployees();
    updateList();
    form.reset();
    form.querySelector("button[type='submit']").textContent = "Submit";
});

updateList();