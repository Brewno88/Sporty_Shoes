document.addEventListener('DOMContentLoaded', () => {
  document.getElementById('add-user-form').addEventListener('submit', addUser)
  document.getElementById('add-shoe-form').addEventListener('submit', addShoe)
  document
    .getElementById('edit-user-form')
    .addEventListener('submit', updateUser)
})

function loadUsers() {
  fetch('http://localhost:8090/getUsers')
    .then(response => {
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`)
      }
      return response.json()
    })
    .then(users => {
      const userList = document.getElementById('user-list')
      userList.innerHTML = `
        <table>
          <thead>
        <tr>
          <th>Email</th>
          <th>Password</th>
          <th>Role</th>
          <th>Edit</th>
          <th>Delete</th>
        </tr>
          </thead>
          <tbody>
        ${users
          .map(
            user => `
          <tr>
            <td>${user.email}</td>
            <td>${user.password}</td>
            <td>${user.role}</td>
            <td><button onclick="editUser(${user.id}, '${user.email}', '${user.password}', '${user.role}')">Edit</button></td>
            <td><button onclick="deleteUser(${user.id})">Delete</button></td>
          </tr>
        `
          )
          .join('')}
          </tbody>
        </table>
      `
    })
    .catch(error => console.error('Error loading users:', error))
}

function addUser(event) {
  event.preventDefault()
  const email = document.getElementById('username').value
  const password = document.getElementById('password').value

  fetch('http://localhost:8090/createUser', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ email, password })
  })
    .then(response => response.json())
    .then(user => {
      console.log('User added:', user)
      loadUsers()
    })
    .catch(error => console.error('Error adding user:', error))
}

function deleteUser(userId) {
  fetch(`http://localhost:8090/deleteUser/${userId}`, {
    method: 'DELETE'
  })
    .then(() => {
      console.log('User deleted')
      loadUsers()
    })
    .catch(error => console.error('Error deleting user:', error))
}

function editUser(id, email, password, role) {
  const newEmail = prompt('Enter new email address:', email)
  const newPassword = prompt('Enter new Password:', password)
  const newRole = prompt('Enter new Role:', role)
  if (newEmail && newPassword && newRole) {
    updateUser(id, newEmail, newPassword, newRole)
  } else {
    console.log('Email or password not provided. Update cancelled.')
  }
}

function updateUser(id, email, password, role) {
  fetch(`http://localhost:8090/updateUser/${id}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ email, password, role })
  })
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok')
      }
      return response.json()
    })
    .then(() => {
      loadUsers()
    })
    .catch(error => console.error('Error updating user:', error))
}

function loadShoes() {
  fetch('http://localhost:8090/shoes/getAll')
    .then(response => response.json())
    .then(shoes => {
      const shoeList = document.getElementById('shoe-list')
      shoeList.innerHTML = `
        <table>
          <thead>
            <tr>
              <th>Name</th>
              <th>Price</th>
              <th>Description</th>
              <th>Image</th>
              <th>Edit</th>
              <th>Delete</th>
            </tr>
          </thead>
          <tbody>
            ${shoes
              .map(shoe => {
                console.log(shoe)
                return `
              <tr>
                <td>${shoe.name}</td>
                <td>£${shoe.price}</td>
                <td>${shoe.description}</td>
                <td><img src="/images/${shoe.image}" alt="${shoe.name}" width="50"></td>
                <td><button onclick="editShoe({id:${shoe.id}, name: '${shoe.name}',price:${shoe.price}, description: '${shoe.description}'})">Edit</button></td>
                <td><button onclick="deleteShoe(${shoe.id})">Delete</button></td>
              </tr>
            `
              })
              .join('')}
          </tbody>
        </table>
      `
    })
    .catch(error => console.error('Error loading shoes:', error))
}

function addShoe(event) {
  event.preventDefault()
  const name = document.getElementById('shoe-name').value
  const price = document.getElementById('shoe-price').value
  const description = document.getElementById('shoe-description').value
  const image = document.getElementById('shoe-image').files[0]

  const formData = new FormData()
  formData.append('name', name)
  formData.append('price', price)
  formData.append('description', description)
  formData.append('image', image)

  fetch('http://localhost:8090/shoes/add', {
    method: 'POST',
    body: formData
  })
    .then(response => response.text())
    .then(message => {
      // Add a delay before calling loadShoes
      setTimeout(() => {
        loadShoes()
      }, 1000) // 1 second delay
    })
    .catch(error => console.error('Error adding shoe:', error))
}

function deleteShoe(shoeId) {
  fetch(`http://localhost:8090/shoes/deleteShoe/${shoeId}`, {
    method: 'DELETE'
  })
    .then(() => {
      console.log('Shoe deleted')
      loadShoes()
    })
    .catch(error => console.error('Error deleting shoe:', error))
}

async function editShoe(shoe) {
  const name = prompt('Enter new name:', shoe.name)
  const price = prompt('Enter new price:', shoe.price)
  const description = prompt('Enter new description:', shoe.description)
  const image = document.createElement('input')
  image.type = 'file'
  image.accept = 'image/*'

  const file = await new Promise(resolve => {
    image.onchange = () => resolve(image.files[0])
    image.click()
  })

  if (name && price && description && file) {
    const formData = new FormData()
    formData.append('name', name)
    formData.append('price', price)
    formData.append('description', description)
    formData.append('image', file)

    fetch(`http://localhost:8090/shoes/updateShoe/${shoe.id}`, {
      method: 'PUT',
      body: formData
    })
      .then(response => response.json())
      .then(shoe => {
        console.log('Shoe updated:', shoe)
        loadShoes()
      })
      .catch(error => console.error('Error updating shoe:', error))
  }

  image.type = 'file'
  image.accept = 'image/*'

  image.onchange = async () => {
    const file = image.files[0]
    if (name && price && description && file) {
      const formData = new FormData()
      formData.append('name', name)
      formData.append('price', price)
      formData.append('description', description)
      formData.append('image', file)

      fetch(`http://localhost:8090/shoes/updateShoe/${shoeId}`, {
        method: 'PUT',
        body: formData
      })
        .then(response => response.json())
        .then(shoe => {
          console.log('Shoe updated:', shoe)
          loadShoes()
        })
        .catch(error => console.error('Error updating shoe:', error))
    }
  }
  image.click()
}
