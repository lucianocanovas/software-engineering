import { useState, useEffect } from 'react';
import {
  collection,
  addDoc,
  updateDoc,
  deleteDoc,
  doc,
  onSnapshot,
  Timestamp
} from 'firebase/firestore';
import { db } from './config/firebase';
import './App.css';
import ItemCard from './components/itemCard';

interface Item {
  id: string;
  title: string;
  description: string;
  createdAt: Timestamp;
}

function App() {
  const [items, setItems] = useState<Item[]>([]);
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [editingId, setEditingId] = useState<string | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  // Fetch items from Firestore in real-time
  useEffect(() => {
    const unsubscribe = onSnapshot(
      collection(db, 'items'),
      (snapshot) => {
        const fetchedItems: Item[] = [];
        snapshot.forEach((doc) => {
          fetchedItems.push({
            id: doc.id,
            ...(doc.data() as Omit<Item, 'id'>)
          });
        });
        // Sort by creation date (newest first)
        fetchedItems.sort((a, b) => b.createdAt.toMillis() - a.createdAt.toMillis());
        setItems(fetchedItems);
        setLoading(false);
      },
      (err) => {
        setError('Error loading items: ' + err.message);
        setLoading(false);
      }
    );

    return () => unsubscribe();
  }, []);

  // Clear success/error messages after 3 seconds
  useEffect(() => {
    if (success) {
      const timer = setTimeout(() => setSuccess(''), 3000);
      return () => clearTimeout(timer);
    }
  }, [success]);

  useEffect(() => {
    if (error) {
      const timer = setTimeout(() => setError(''), 3000);
      return () => clearTimeout(timer);
    }
  }, [error]);

  // CREATE
  const handleCreate = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!title.trim()) {
      setError('Please enter a title');
      return;
    }

    try {
      await addDoc(collection(db, 'items'), {
        title: title.trim(),
        description: description.trim(),
        createdAt: Timestamp.now()
      });
      setTitle('');
      setDescription('');
      setSuccess('Item created successfully!');
    } catch (err) {
      setError('Error creating item: ' + (err instanceof Error ? err.message : 'Unknown error'));
    }
  };

  // UPDATE
  const handleUpdate = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!editingId) return;
    if (!title.trim()) {
      setError('Please enter a title');
      return;
    }

    try {
      await updateDoc(doc(db, 'items', editingId), {
        title: title.trim(),
        description: description.trim()
      });
      setTitle('');
      setDescription('');
      setEditingId(null);
      setSuccess('Item updated successfully!');
    } catch (err) {
      setError('Error updating item: ' + (err instanceof Error ? err.message : 'Unknown error'));
    }
  };

  // DELETE
  const handleDelete = async (id: string) => {
    try {
      await deleteDoc(doc(db, 'items', id));
      setSuccess('Item deleted successfully!');
    } catch (err) {
      setError('Error deleting item: ' + (err instanceof Error ? err.message : 'Unknown error'));
    }
  };

  // EDIT (populate form)
  const handleEdit = (item: { id: string; title: string; description?: string }) => {
    setTitle(item.title);
    setDescription(item.description ?? '');
    setEditingId(item.id);
    // Scroll to form
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  // CANCEL EDIT
  const handleCancel = () => {
    setTitle('');
    setDescription('');
    setEditingId(null);
  };

  return (
    <div className="container">
      <h1>CRUD App</h1>

      {error && <div className="error">{error}</div>}
      {success && <div className="success">{success}</div>}

      {/* Form Section */}
      <div className="form-section">
        <h2 style={{ marginBottom: '20px', color: '#333' }}>
          {editingId ? 'Edit Item' : 'Create New Item'}
        </h2>
        <form onSubmit={editingId ? handleUpdate : handleCreate}>
          <div className="form-group">
            <label htmlFor="title">Title</label>
            <input
              id="title"
              type="text"
              placeholder="Enter item title"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
            />
          </div>

          <div className="form-group">
            <label htmlFor="description">Description</label>
            <textarea
              id="description"
              placeholder="Enter item description (optional)"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
            />
          </div>

          <div className="form-actions">
            <button type="submit" className="btn-submit">
              {editingId ? 'Update Item' : 'Create Item'}
            </button>
            {editingId && (
              <button type="button" className="btn-cancel" onClick={handleCancel}>
                Cancel
              </button>
            )}
          </div>
        </form>
      </div>

      {/* Items List Section */}
      <div className="items-section">
        <h2>Items ({items.length})</h2>

        {loading ? (
          <div className="loading">Loading items...</div>
        ) : items.length === 0 ? (
          <div className="empty-state">
            <p>No items yet.</p>
          </div>
        ) : (
          <div className="items-list">
            {items.map((item) => (
              <ItemCard
                key={item.id}
                item={item}
                onEdit={handleEdit}
                onDelete={handleDelete}
              />
            ))}
          </div>
        )}
      </div>
    </div>
  );
}

export default App;