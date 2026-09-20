import type React from "react";

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
