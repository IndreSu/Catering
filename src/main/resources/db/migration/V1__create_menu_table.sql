CREATE TABLE IF NOT EXISTS MENU (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY,
  title VARCHAR(30) NOT NULL,

  CONSTRAINT MENU_PK PRIMARY KEY (id)
);