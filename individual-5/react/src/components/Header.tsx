import type React from "react";

/**
 * =============================================================================
 * COMPONENTE: Header
 * =============================================================================
 * CONCEPTO TEÓRICO EN REACT:
 * Un componente funcional (Functional Component) es una función de JavaScript que
 * retorna elementos JSX para describir qué debe renderizarse en la interfaz de usuario.
 *
 * En este caso, Header es un componente de presentación (stateless) que renderiza
 * la barra superior fija de la aplicación utilizando las clases semánticas de Semantic UI.
 */
const Header: React.FC = () => {
  return (
    <div className="ui fixed menu shadow-sm">
      <div className="ui container center" style={{ justifyContent: "center", padding: "12px 0" }}>
        <h2 className="ui header text-primary" style={{ margin: 0, display: "flex", alignItems: "center", gap: "10px" }}>
          <i className="address book icon"></i>
          Contact Manager
        </h2>
      </div>
    </div>
  );
};

export default Header;
