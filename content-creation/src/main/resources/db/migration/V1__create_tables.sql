CREATE TYPE content_creation.content_status
	AS ENUM ('DRAFT', 'SUBMITTED', 'APPROVED', 'REJECTED');

CREATE TABLE content_creation.content_items (
    id UUID PRIMARY KEY,
    title VARCHAR(256) NOT NULL,
    description TEXT NOT NULL,
    body TEXT NOT NULL,
    author_id UUID NOT NULL,
    status content_creation.content_status DEFAULT 'DRAFT',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE content_creation.revisions (
    id UUID PRIMARY KEY,
    content_item_id UUID NOT NULL REFERENCES content_creation.content_items(id),
    revision_number INT NOT NULL,
    description TEXT NOT NULL,
    title_delta VARCHAR(256) NOT NULL,
    description_delta TEXT NOT NULL,
    body_delta TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE content_creation.tags (
    id UUID PRIMARY KEY,
    name VARCHAR(64) UNIQUE NOT NULL
);

CREATE TABLE content_creation.content_item_tags (
    content_item_id UUID NOT NULL REFERENCES content_creation.content_items(id),
    tag_id UUID NOT NULL REFERENCES content_creation.tags(id),
    PRIMARY KEY (content_item_id, tag_id)
);
