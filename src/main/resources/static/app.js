const API_BASE = 'http://localhost:8080/api/tasks';

const alertsEl = document.getElementById('alerts');
const tasksList = document.getElementById('tasksList');
const emptyHint = document.getElementById('emptyHint');
const addForm = document.getElementById('addForm');
const titleInput = document.getElementById('titleInput');

// helper: create simple Tailwind alert
function showAlert(message, variant = 'red', timeout = 4000) {
  const colors = {
    red: 'bg-red-50 border-red-300 text-red-700',
    green: 'bg-green-50 border-green-300 text-green-700',
    yellow: 'bg-yellow-50 border-yellow-300 text-yellow-700',
    blue: 'bg-blue-50 border-blue-300 text-blue-700'
  };
  const classes = colors[variant] || colors.red;

  const wrapper = document.createElement('div');
  wrapper.className = `border ${classes} px-3 py-2 rounded flex justify-between items-center`;
  wrapper.innerHTML = `<div class=\"text-sm\">${escapeHtml(message)}</div>`;

  const closeBtn = document.createElement('button');
  closeBtn.className = 'ml-3 text-sm font-medium';
  closeBtn.textContent = '×';
  closeBtn.addEventListener('click', () => wrapper.remove());
  wrapper.appendChild(closeBtn);

  alertsEl.appendChild(wrapper);
  if (timeout) setTimeout(() => wrapper.remove(), timeout);
}

function escapeHtml(str) {
  if (!str) return '';
  return str.replaceAll('&', '&amp;').replaceAll('<', '&lt;').replaceAll('>', '&gt;').replaceAll('"', '&quot;').replaceAll("'", '&#039;');
}

async function fetchTodos() {
  try {
    const res = await fetch(API_BASE);
    if (!res.ok) throw new Error('Failed to load tasks');
    const tasks = await res.json();
    renderTasks(tasks);
  } catch (err) {
    showAlert('Could not load tasks — is the backend running?', 'red', 8000);
    console.error(err);
  }
}

function renderTasks(tasks) {
  tasksList.innerHTML = '';
  if (!tasks || tasks.length === 0) {
    emptyHint.style.display = 'block';
    return;
  }
  emptyHint.style.display = 'none';

  tasks.forEach(task => {
    const li = document.createElement('li');
    li.className = 'bg-white p-3 rounded shadow-sm flex justify-between items-center';
    li.dataset.id = task.id;

    const left = document.createElement('div');
    left.className = 'flex items-center gap-3';

    const titleSpan = document.createElement('span');
    titleSpan.innerHTML = escapeHtml(task.title);
    if (task.done) titleSpan.classList.add('task-done');

    left.appendChild(titleSpan);

    const right = document.createElement('div');

    const toggleBtn = document.createElement('button');
    toggleBtn.className = 'px-3 py-1 border rounded mr-2 text-sm';
    toggleBtn.textContent = task.done ? 'Mark Pending' : 'Mark Done';
    toggleBtn.addEventListener('click', () => handleToggle(task.id, li, titleSpan, toggleBtn));

    const deleteBtn = document.createElement('button');
    deleteBtn.className = 'px-3 py-1 bg-red-600 text-white rounded text-sm';
    deleteBtn.textContent = 'Delete';
    deleteBtn.addEventListener('click', () => handleDelete(task.id, li));

    right.appendChild(toggleBtn);
    right.appendChild(deleteBtn);

    li.appendChild(left);
    li.appendChild(right);

    tasksList.appendChild(li);
  });
}

addForm.addEventListener('submit', async (ev) => {
  ev.preventDefault();
  const title = titleInput.value.trim();
  if (title.length < 3) {
    showAlert('Title must be at least 3 characters', 'yellow');
    return;
  }

  try {
    const res = await fetch(API_BASE, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ title })
    });

    if (res.status === 400) {
      const err = await res.json().catch(() => ({ error: 'Title is required' }));
      showAlert(err.error || 'Bad Request', 'yellow');
      return;
    }

    if (!res.ok) throw new Error('Server error');

    await fetchTodos();
    titleInput.value = '';
    showAlert('Task created', 'green', 2000);
  } catch (err) {
    console.error(err);
    showAlert('Could not create task — check backend', 'red');
  }
});

async function handleToggle(id, li, titleSpan, toggleBtn) {
  try {
    const res = await fetch(`${API_BASE}/${id}/toggle`, { method: 'PUT' });
    if (res.status === 404) {
      const err = await res.json().catch(() => ({error: 'Not found'}));
      showAlert(err.error || 'Not found', 'yellow');
      return;
    }
    if (!res.ok) throw new Error('Toggle failed');

    const isDone = titleSpan.classList.toggle('task-done');
    toggleBtn.textContent = isDone ? 'Mark Pending' : 'Mark Done';
  } catch (err) {
    console.error(err);
    showAlert('Could not toggle task — check backend', 'red');
  }
}

async function handleDelete(id, li) {
  if (!confirm('Delete this task?')) return;
  try {
    const res = await fetch(`${API_BASE}/${id}`, { method: 'DELETE' });
    if (res.status === 404) {
      const err = await res.json().catch(() => ({error: 'Not found'}));
      showAlert(err.error || 'Not found', 'yellow');
      return;
    }
    if (!res.ok) throw new Error('Delete failed');

    li.remove();
    if (!tasksList.children.length) emptyHint.style.display = 'block';
    showAlert('Task deleted', 'green', 2000);
  } catch (err) {
    console.error(err);
    showAlert('Could not delete task — check backend', 'red');
  }
}

// initialize
fetchTodos();