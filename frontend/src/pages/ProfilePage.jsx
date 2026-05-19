import { useEffect, useState } from "react";
import api from "../api/axiosConfig";

const ProfilePage = () => {
  const [profile, setProfile] = useState(null);

  useEffect(() => {
    api.get("/profile")
      .then((res) => setProfile(res.data))
      .catch(() => alert("Please login first"));
  }, []);

  if (!profile) return <h2>Loading profile...</h2>;

  return (
    <div className="card" style={{ maxWidth: "500px", margin: "2rem auto" }}>
      <h2>My Profile</h2>
      <p><b>Username:</b> {profile.username}</p>
      <p><b>Email:</b> {profile.email || "Not available"}</p>
    </div>
  );
};

export default ProfilePage;