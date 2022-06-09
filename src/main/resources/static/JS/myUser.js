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
            <td id="username${user.id}">${user.username} </td>
            <td id="age${user.id}">${user.age}</td>
            <td id="city${user.id}">${user.city}</td>
            <td id="userRole${user.id}">${userRoles}</td>`;
            document.getElementById("oneUser").innerHTML = out;
            $('#headerSpan').text(`${user.username} with roles: ${userRoles}`);
        })
}
getUser();