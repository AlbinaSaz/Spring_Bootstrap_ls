function getUser() {
    let out = '';
    let userRoles = '';
    fetch('http://localhost:8080/api/user/')
        .then(response => {
            return response.json();
        })
        .then(user => {
            for (let i = 0; i < user.roles.length; i++) {
                userRoles += user.roles[i].authority.replace('ROLE_', ' ');
            }
            out = `<td id="id${user.id}"> ${user.id}</td>
            <td id="username${user.id}">${user.username}</td>
            <td id="city${user.id}">${user.city} </td>
            <td id="age${user.id}">${user.age}</td>
            <td id="userRole${user.id}">${userRoles}</td>`;
            document.getElementById("oneUser").innerHTML = out;
            $('#headerSpan').text(`${user.username} with roles: ${userRoles}`);
        })
}

function getAllUsers() {
    let out = '';
    fetch('http://localhost:8080/api/admin/users')
        .then(response => {
            return response.json();
        })
        .then(users => {
            users.forEach(user => {
                let userRoles = '';
                for (let i = 0; i < user.roles.length; i++) {
                    userRoles += user.roles[i].authority.replace('ROLE_', ' ');
                }
                out += `<tr><td id="id${user.id}">${user.id}</td>
            <td id="username${user.id}">${user.username}</td>
            <td id="city${user.id}">${user.city} </td>
            <td id="age${user.id}">${user.age}</td>
            <td id="userRole${user.id}">${userRoles}</td>
            <td id="${user.id}"> 
                <button type="button" class="btn btn-info" data-toggle="modal" 
                data-target="#edit" id="edit${user.id}" onclick="fillModalEdit(${user.id})"  >
                Edit</button>
            </td>
            <td> 
                <button type="button" class="btn btn-danger" data-toggle="modal" 
                data-target="#delete" id="delete${user.id}" onclick="fillModalDelete(${user.id})">
                Delete</button>
            </td>
            <td></td></tr>`;
            });
            document.getElementById('allUsers').innerHTML = out;
        })
}

function fillModalEdit(id) {
    $('#idEdit').val($('#id' + id).text());
    $('#usernameEdit').val($('#username' + id).text());
    $('#cityEdit').val($('#city' + id).text());
    $('#ageEdit').val($('#age' + id).text());
    $('#passwordEdit').val("");
}

function fillModalDelete(id) {
    $('#idDelete').val($('#id' + id).text());
    $('#usernameDelete').val($('#username' + id).text());
    $('#cityDelete').val($('#city' + id).text());
    $('#ageDelete').val($('#age' + id).text());
}

function getRoles() {
    let opt = '';
    fetch("http://localhost:8080/api/admin/roles")
        .then(response => {
            return response.json()
        })
        .then(roles => {
            roles.forEach(role => {
                document.getElementById('roleUserEdit')
                opt += `<option value="${role.id}">${role.authority}</option>`
            });
            document.getElementById('roleUserEdit').innerHTML = opt;
            document.getElementById('roleUserNew').innerHTML = opt;
        })
}

getRoles();

// DELETE USER
document.getElementById('delete').addEventListener('submit', e => {
    e.preventDefault();
    const id = document.getElementById('idDelete').value;
    fetch('http://localhost:8080/api/admin/users/' + id, {
        method: 'DELETE'
    }).then(() => {
        $('#delete').modal('hide');
    }).then(() => getAllUsers());
})
// -----------------------------EDIT USER----------------------------------
document.getElementById('modalEditUser').addEventListener('submit', e => {
    e.preventDefault();
    let array = [];
    $('#roleUserEdit option:selected').each(function () {
        array.push({id: $(this).val(), authority: $(this).text()}); //username: $(this).text(),
    });
    fetch('http://localhost:8080/api/admin/users', {
        method: 'PUT',
        headers: {
            'Content-type': 'application/json'
        },
        body: JSON.stringify({
            id: document.getElementById('idEdit').value,
            username: document.getElementById('usernameEdit').value,
            city: document.getElementById('cityEdit').value,
            age: document.getElementById('ageEdit').value,
            password: document.getElementById('passwordEdit').value,
            roles: array,
        })
    }).then(() => {
        $('#edit').modal('hide');
    }).then(() => getAllUsers());
})


getAllUsers();
getUser();

document.querySelector('.newUser').addEventListener('submit', e => {
    e.preventDefault();
    let array = [];
    $('#roleUserNew option:selected').each(function () {
        array.push({id: $(this).val(), authority: $(this).text()}); //username: $(this).text(),
    });
    fetch('http://localhost:8080/api/admin/users', {
        method: 'POST',
        headers: {
            'Content-type': 'application/json'
        },
        body: JSON.stringify({
            username: document.getElementById('username').value,
            city: document.getElementById('city').value,
            age: document.getElementById('age').value,
            password: document.getElementById('password').value,
            roles: array,
        })
    })
        .then(response => response.json())
        .then(() => document.querySelector('.newUser').reset())
        .then(() => getAllUsers());


})