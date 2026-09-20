import type React from "react";
import type { Contact } from "../types/contact.ts";
import userAvatar from "../images/user.svg";

interface ContactCardProps {
  contact: Contact;
  clickHandler: (id: string) => void;
}

/**
 * =============================================================================
 * COMPONENTE: ContactCard
 * =============================================================================
 * CONCEPTOS TEÓRICOS EN REACT:
 * 1. PROPS (Propiedades):
 *    Permite pasar información de padres a hijos en una arquitectura de flujo
 *    unidireccional de datos (Unidirectional Data Flow). ContactCard recibe
 *    el objeto de contacto y la función `clickHandler` para notificar la eliminación.
 *
 * 2. DESESTRUCTURACIÓN (Destructuring):
 *    Extrae las propiedades `id`, `name` y `email` de forma limpia y legible.
 *
 * 3. PASO DE ARGUMENTOS EN HANDLERS:
 *    Se utiliza una función flecha anónima `() => clickHandler(id)` para evitar
 *    que la función se ejecute inmediatamente al renderizar el componente.
 */
const ContactCard: React.FC<ContactCardProps> = ({ contact, clickHandler }) => {
  const { id, name, email } = contact;

  return (
    <div
      className="item"
      style={{
        display: "flex",
        alignItems: "center",
        justifyContent: "space-between",
        padding: "14px 10px",
        borderBottom: "1px solid #f1f5f9"
      }}
    >
      <div style={{ display: "flex", alignItems: "center", gap: "14px" }}>
        <img
          className="ui avatar image"
          src={userAvatar}
          alt="Avatar de usuario"
          style={{ width: "42px", height: "42px" }}
        />
        <div className="content">
          <div className="header" style={{ fontSize: "1.05rem", fontWeight: "600", color: "#1e293b" }}>
            {name}
          </div>
          <div style={{ color: "#64748b", fontSize: "0.9rem", display: "flex", alignItems: "center", gap: "5px" }}>
            <i className="envelope outline icon" style={{ fontSize: "0.85rem" }}></i>
            {email}
          </div>
        </div>
      </div>

      <div>
        <button
          type="button"
          className="ui icon button basic red circular"
          title="Eliminar contacto"
          onClick={() => clickHandler(id)}
          style={{ cursor: "pointer", transition: "all 0.2s ease" }}
        >
          <i className="trash alternate outline icon" style={{ margin: 0 }}></i>
        </button>
      </div>
    </div>
  );
};

export default ContactCard;
