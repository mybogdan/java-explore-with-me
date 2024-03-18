drop TABLE IF EXISTS compilation_event;
drop TABLE IF EXISTS compilations;
drop TABLE IF EXISTS requests;
drop TABLE IF EXISTS event_comments;
drop TABLE IF EXISTS events;
drop TABLE IF EXISTS users;
drop TABLE IF EXISTS locations;
drop TABLE IF EXISTS categories;

create TABLE IF NOT EXISTS categories (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  name VARCHAR(255) NOT NULL,
  unique(name),
  CONSTRAINT pk_category PRIMARY KEY (id)
);

create TABLE IF NOT EXISTS users (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  unique(email),
  unique(name),
  CONSTRAINT pk_user PRIMARY KEY (id)
);

create TABLE IF NOT EXISTS locations (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  lat NUMERIC(9,6) NOT NULL,
  lon NUMERIC(9,6) NOT NULL,
  CONSTRAINT pk_location PRIMARY KEY(id)
);

create TABLE IF NOT EXISTS events (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  title VARCHAR(255) NOT NULL,
  annotation VARCHAR(2000) NOT NULL,
  description VARCHAR(7000) NOT NULL,
  paid boolean,
  participant_limit INTEGER,
  event_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  created_on TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  published_on TIMESTAMP WITHOUT TIME ZONE,
  category_id BIGINT references categories(id) NOT NULL,
  location_id BIGINT references locations(id) NOT NULL,
  initiator_id BIGINT references users(id) NOT NULL,
  state VARCHAR(255) NOT NULL,
  request_moderation boolean,
  confirmed_requests INTEGER NOT NULL,
  CONSTRAINT pk_event PRIMARY KEY (id)
);

create TABLE IF NOT EXISTS requests (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  created_on TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  event_id BIGINT references events(id) NOT NULL,
  user_id BIGINT references users(id) NOT NULL,
  state VARCHAR(255) NOT NULL,
  UNIQUE(event_id, user_id),
  CONSTRAINT pk_request PRIMARY KEY(id)
);

create TABLE IF NOT EXISTS compilations (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  title VARCHAR(255) NOT NULL,
  pinned boolean NOT NULL,
  CONSTRAINT pk_compilations PRIMARY KEY(id)
);

create TABLE IF NOT EXISTS compilation_event (
  event_id BIGINT references events(id) ON DELETE CASCADE NOT NULL,
  compilation_id BIGINT references compilations(id) ON DELETE CASCADE NOT NULL,
  CONSTRAINT pk_compilation_event PRIMARY KEY(event_id, compilation_id)
);

create TABLE IF NOT EXISTS event_comments (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  event_id BIGINT references events(id) ON DELETE CASCADE NOT NULL,
  user_id BIGINT references users(id) ON DELETE CASCADE NOT NULL,
  comment VARCHAR(2000) NOT NULL,
  created_on TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  changed_on TIMESTAMP WITHOUT TIME ZONE,
  CONSTRAINT pk_event_comments PRIMARY KEY(id)
);



