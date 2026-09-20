import { useState } from "react";
import type React from "react";
import type { Contact } from "../types/contact.ts";
import ContactCard from "./ContactCard.tsx";

interface ContactListProps {
  contacts: Contact[];
  getContactId: (id: string) => void;
}

/**
 * =============================================================================
 * COMPONENTE: ContactList
 * =============================================================================
 * CONCEPTOS TEÓRICOS EN REACT:
 * 1. RENDERIZADO DE LISTAS (.map()):
 *    En React, las colecciones de datos se transforman en elementos JSX utilizando
 *    el método funcional `.map()`.
 *
 * 2. PROP "key" Y ALGORITMO DE RECONCILIACIÓN:
 *    Cada elemento generado en un bucle o lista DEBE tener una propiedad `key` única
 *    (en este caso, `contact.id`). Esto le permite al motor del Virtual DOM identificar
 *    con precisión qué elementos fueron modificados, agregados o eliminados sin necesidad
 *    de re-renderizar todo el árbol del DOM, maximizando el rendimiento.
 *
 * 3. BÚSQUEDA Y FILTRADO REACTIVO:
 *    Permite filtrar la lista de contactos en tiempo real mediante un input de búsqueda
 *    (funcionalidad avanzada explicada en la guía audiovisual de Dipesh Malvia).
 */
const ContactList: React.FC<ContactListProps> = ({ contacts, getContactId }) => {
  const [searchTerm, setSearchTerm] = useState<string>("");

  const deleteContactHandler = (id: string) => {
    getContactId(id);
  };

  // Filtrado reactivo por nombre o correo electrónico
  const filteredContacts = contacts.filter((contact) => {
    const term = searchTerm.toLowerCase();
    return (
      contact.name.toLowerCase().includes(term) ||
      contact.email.toLowerCase().includes(term)
    );
  });

  const renderContactList = filteredContacts.map((contact) => {
    return (
      <ContactCard
        key={contact.id}
        contact={contact}
        clickHandler={deleteContactHandler}
      />
    );
  });

  return (
    <div className="ui segment" style={{ marginTop: "20px", padding: "20px" }}>
      <div
        style={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          flexWrap: "wrap",
          gap: "10px",
          marginBottom: "15px",
        }}
      >
        <h3 className="ui header" style={{ margin: 0, display: "flex", alignItems: "center", gap: "8px" }}>
          <i className="users icon text-primary"></i>
          Contact List
          <span className="ui label circular blue">{contacts.length}</span>
        </h3>

        {/* Barra de Búsqueda Interactiva (Guía Audiovisual) */}
        <div className="ui icon input" style={{ minWidth: "260px" }}>
          <input
            type="text"
            placeholder="Buscar contactos..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
          <i className="search icon"></i>
        </div>
      </div>

      <div className="ui celled list" style={{ borderTop: "1px solid #e2e8f0" }}>
        {renderContactList.length > 0 ? (
          renderContactList
        ) : (
          <div className="ui placeholder segment center aligned" style={{ padding: "30px", border: "none" }}>
            <div className="ui icon header">
              <i className="search outline icon text-muted"></i>
              {searchTerm
                ? "No se encontraron contactos que coincidan con la búsqueda."
                : "No hay contactos registrados todavía. ¡Agrega uno arriba!"}
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default ContactList;
