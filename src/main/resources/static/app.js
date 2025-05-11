const HOST_URL="http://localhost:8080"
const API_URL = HOST_URL + '/api';
let imageId = null;

document.addEventListener("DOMContentLoaded", async () => {
  await loadComponents();
  setLanguage(currentLang);
  addFormHandlers();
  updateAuthUI();  // تأكد من استدعاء الدالة بعد تحميل DOM بالكامل
});

async function loadComponents() {
  await loadHTML('components/navbar.html', 'navbar-container');
  await loadHTML('components/login-popup.html', 'popup-login-container');
  await loadHTML('components/reg-popup.html', 'popup-reg-container');
  await loadHTML('components/popup-lost.html', 'popup-lost-container');
  await loadHTML('components/popup-found.html', 'popup-found-container');
}

async function loadHTML(path, targetId) {
  const res = await fetch(path);
  const html = await res.text();
  document.getElementById(targetId).innerHTML = html;
}

function togglePopup(type) {
  document.getElementById(`popup-${type}`).classList.toggle('hidden');
}
function toggleMobileMenu() {
  document.getElementById('mobile-menu').classList.toggle('hidden');
}
function toggleLogin() {
  document.getElementById('login-popup').classList.toggle('hidden');
}
function toggleRegister() {
  document.getElementById('register-popup').classList.toggle('hidden');
}

function removeToken(){
    localStorage.removeItem('token');
    updateAuthUI();
}

document.body.addEventListener('submit', async function (e) {
  const form = e.target;

  if (form.id === 'loginForm') {
    e.preventDefault();

    const formData = new FormData(form);
    const username = formData.get('username');
    const password = formData.get('password');

    const response = await fetch(`${HOST_URL}/auth/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ username, password })
    });

    if (response.ok) {
      const data = await response.json();
      localStorage.setItem('token', data.token);
      updateAuthUI();
      alert(i18n[currentLang].loginSuccess);
      toggleLogin();
    } else {
      alert(i18n[currentLang].loginError);
    }
  }
});
document.body.addEventListener('submit', async function (e) {
  const form = e.target;

  if (form.id === 'registerForm') {
    e.preventDefault();

    const formData = new FormData(form);
    const username = formData.get('username');
    const password = formData.get('password');
    const email = formData.get('email');
    const confirmPassword = formData.get('confirmPassword');

    const response = await fetch(`${HOST_URL}/auth/register`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ username, password,confirmPassword,email,username })
    });

    if (response.ok) {
      const data = await response.json();
      localStorage.setItem('token', data.token);
      updateAuthUI();
      alert(i18n[currentLang].registrationSuccess);
      toggleRegister();
    } else {
      alert(i18n[currentLang].registrationFailed);
    }
  }
});

function addFormHandlers() {
  document.body.addEventListener('change', async function (e) {
    if (e.target.id === 'imageInput') {
      const file = e.target.files[0];
      if (file) {
        const formData = new FormData();
        formData.append('image', file);

        const response = await fetch(`${API_URL}/images`, {
          method: 'POST',
          headers: { 'Authorization': `Bearer ${localStorage.getItem('token')}` },
          body: formData
        });

        if (response.ok) {
          const data = await response.text();
          console.log(data)
          imageId = data;
        } else {
          alert(i18n[currentLang].uploadFailed);
        }
      }
    }
  });

  document.body.addEventListener('submit', async function (e) {
    const form = e.target;
    if (form.id === 'lostForm' || form.id === 'foundForm') {
      e.preventDefault();

      const formData = new FormData(form);
      const type = form.id === 'lostForm' ? 'lost' : 'found';

      const data = {
        name: formData.get('name'),
        description: formData.get('description'),
        category: formData.get('category'),
        imageId: imageId,
        [`${type}Date`]: formData.get(`${type}Date`)
      };

      const token = localStorage.getItem('token');
      if (!token) return alert(i18n[currentLang].loginFirst);

      const response = await fetch(`${API_URL}/items/${type}`, {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
      });

      if (response.ok) {
        alert(i18n[currentLang].submissionSuccess);
        togglePopup(type);
        form.reset();
        loadItems(type);
      } else {
        alert(i18n[currentLang].submissionError);
      }
    }
  });
}

async function loadItems(type) {
  const container = document.getElementById('items-container');
  container.innerHTML = i18n[currentLang].loading;

  try {
    const res = await fetch(`${API_URL}/items/${type}`);
    const items = await res.json();

    container.innerHTML = '';
    items.forEach(item => {
      container.innerHTML += `
        <div class="bg-white rounded shadow p-4">
          <img src="${API_URL}/${item.imageUrl || ''}" alt="صورة" class="w-full h-48 object-cover mb-2 rounded">
          <h3 class="text-lg font-bold">${item.name}</h3>
          <p class="text-sm text-gray-600">${item.description}</p>
          <span class="text-xs text-gray-400">${item.category}</span>
        </div>
      `;
    });
  } catch (err) {
    container.innerHTML = i18n[currentLang].submissionError;
  }
}

function isUserLoggedIn() {
  return !!localStorage.getItem('token');
}

function updateAuthUI() {
  const hiddenIfNotLoggedIn = document.getElementsByClassName('hidden-if-not-loggedin');
  const hiddenIfLoggedIn = document.getElementsByClassName('hidden-if-loggedin');

  Array.from(hiddenIfNotLoggedIn).forEach(element => {
    if (isUserLoggedIn()) {
      element.classList.remove('hidden');
    } else {
      element.classList.add('hidden');
    }
  });

  Array.from(hiddenIfLoggedIn).forEach(element => {
    if (isUserLoggedIn()) {
      element.classList.add('hidden');
    } else {
      element.classList.remove('hidden');
    }
  });
}
