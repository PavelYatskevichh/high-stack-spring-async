INSERT INTO content_creation.content_items (id, title, description, body, author_id, status, created_at)
VALUES
    ('11111111-1111-1111-1111-111111111111', 'How to Start Your Own Business from Scratch',
    'This article provides a step-by-step guide for aspiring entrepreneurs looking to start their own business.',
    'Starting a business involves planning, securing, and executing your vision. This article walks you through these steps.',
    '00000000-0000-0000-0000-000000000001', 'DRAFT', CURRENT_TIMESTAMP),
    ('11111111-1111-1111-1111-111111111112', 'The Role of Big Data in Modern Marketing',
    'Learn how big data analytics is transforming marketing strategies and consumer insights.',
    'Big data helps companies predict trends, understand customer behavior, and make data-driven decisions. Learn how this is achieved.',
    '00000000-0000-0000-0000-000000000001', 'SUBMITTED', CURRENT_TIMESTAMP),
    ('11111111-1111-1111-1111-111111111113', 'A Comprehensive Guide to Healthy Eating',
    'A detailed overview of healthy eating habits and nutritional guidelines to improve overall health.',
    'Healthy eating involves understanding food and controlling portion sizes. Learn the key principles here.',
    '00000000-0000-0000-0000-000000000001', 'APPROVED', CURRENT_TIMESTAMP),
    ('11111111-1111-1111-1111-111111111114', '10 Tips for Writing a Winning Resume',
    'Crafting a resume that stands out can be challenging. These tips help you create one that shines.',
    'A strong resume can give a dream job. Follow these ten steps to create one that highlights your skills.',
    '00000000-0000-0000-0000-000000000001', 'REJECTED', CURRENT_TIMESTAMP),
    ('11111111-1111-1111-1111-111111111115', 'Exploring the Mysteries of Quantum Physics',
    'Quantum physics is often considered one of the most complex fields of science. This article simplifies its core concepts.',
    'Quantum physics explores the matter and energy at the smallest scales. Concepts like superposition and entanglement are covered here.',
    '00000000-0000-0000-0000-000000000001', 'DRAFT', CURRENT_TIMESTAMP),
    ('11111111-1111-1111-1111-111111111116', 'The Importance of Emotional Intelligence in Leadership',
    'Discover why emotional intelligence is a crucial skill for effective leadership in today''s workplaces.',
    'Emotional intelligence includes communication skills. This guide helps you understand and develop these traits.',
    '00000000-0000-0000-0000-000000000001', 'SUBMITTED', CURRENT_TIMESTAMP);

INSERT INTO content_creation.revisions (id, content_item_id, revision_number, description, title_delta, description_delta, body_delta, created_at)
VALUES
    ('22222222-2222-2222-2222-222222222221', '11111111-1111-1111-1111-111111111111',
    1, 'Revised the introduction to provide a more engaging hook for readers.',
    '=43', '=106', '=47	+ funding	=72', CURRENT_TIMESTAMP),
    ('22222222-2222-2222-2222-222222222222', '11111111-1111-1111-1111-111111111111',
    2, 'Adjusted formatting and removed unnecessary jargon for better readability.',
    '=43', '=106', '=47	+ funding	=65	+ critical	=7', CURRENT_TIMESTAMP),
    ('22222222-2222-2222-2222-222222222225', '11111111-1111-1111-1111-111111111114',
    1, 'Removed redundant paragraphs and tightened the narrative for better flow.',
    '=36', '=97', '=20	-6	+open doors to your	=77', CURRENT_TIMESTAMP),
    ('22222222-2222-2222-2222-222222222226', '11111111-1111-1111-1111-111111111114',
    2, 'Incorporated feedback from the author to clarify ambiguous statements.',
    '=36', '=97', '=20	-6	+open doors to your	=75	+s and achievement	=2', CURRENT_TIMESTAMP),
    ('22222222-2222-2222-2222-222222222227', '11111111-1111-1111-1111-111111111115',
    1, 'Added real-life examples to illustrate the main ideas effectively.',
    '=42', '=121', '=29	+behavior of 	=104', CURRENT_TIMESTAMP),
    ('22222222-2222-2222-2222-222222222228', '11111111-1111-1111-1111-111111111116',
    1, 'Updated references and sources to ensure the article reflects the latest developments.',
    '=54', '=103', '=32	+self-awareness, empathy, and 	=79', CURRENT_TIMESTAMP);

INSERT INTO content_creation.tags (id, name)
VALUES
    ('33333333-3333-3333-3333-333333333331', 'Technology'),
    ('33333333-3333-3333-3333-333333333332', 'Education'),
    ('33333333-3333-3333-3333-333333333333', 'Science'),
    ('33333333-3333-3333-3333-333333333334', 'Health'),
    ('33333333-3333-3333-3333-333333333335', 'Lifestyle'),
    ('33333333-3333-3333-3333-333333333336', 'Business');

INSERT INTO content_creation.content_item_tags (content_item_id, tag_id)
VALUES
    ('11111111-1111-1111-1111-111111111111', '33333333-3333-3333-3333-333333333331'),
    ('11111111-1111-1111-1111-111111111111', '33333333-3333-3333-3333-333333333332'),
    ('11111111-1111-1111-1111-111111111112', '33333333-3333-3333-3333-333333333333'),
    ('11111111-1111-1111-1111-111111111113', '33333333-3333-3333-3333-333333333331'),
    ('11111111-1111-1111-1111-111111111113', '33333333-3333-3333-3333-333333333334'),
    ('11111111-1111-1111-1111-111111111114', '33333333-3333-3333-3333-333333333336'),
    ('11111111-1111-1111-1111-111111111115', '33333333-3333-3333-3333-333333333332'),
    ('11111111-1111-1111-1111-111111111116', '33333333-3333-3333-3333-333333333335');
