-- CREATE TABLE IF NOT EXISTS users (
--   id INT AUTO_INCREMENT PRIMARY KEY,
--   username VARCHAR(250) NOT NULL,
--   email VARCHAR(250) NOT NULL,
--   password VARCHAR(250) NOT NULL
-- );

-- CREATE TABLE IF NOT EXISTS roles (
--   id INT AUTO_INCREMENT PRIMARY KEY,
--   name VARCHAR(50) NOT NULL
-- );

-- CREATE TABLE IF NOT EXISTS user_roles (
--   user_id INT NOT NULL,
--   role_id INT NOT NULL,
--   PRIMARY KEY (user_id, role_id),
--   FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
--   FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
-- );

INSERT INTO users (username, email, password) VALUES
  ('Guillaume', 'guillaume.nagiel@gmail.com', 'guillaume'),
  ('test', 'test.test@test.com', 'test');

INSERT INTO roles (name) VALUES
  ('ROLE_ADMIN'),
  ('ROLE_MODERATOR'),
  ('ROLE_USER');

INSERT INTO user_roles (user_id, role_id) VALUES
  (1, 3),
  (1, 2),
  (1, 1),
  (2, 1);