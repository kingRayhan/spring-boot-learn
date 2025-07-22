-- tag table
CREATE TABLE public.tags (
                             id   BIGSERIAL PRIMARY KEY,
                             name VARCHAR(255) NOT NULL
);

-- junction table user ↔ tag
CREATE TABLE public.user_tags (
                                  user_id BIGINT NOT NULL,
                                  tag_id  BIGINT NOT NULL,
                                  PRIMARY KEY (user_id, tag_id),

    -- each column points to its own parent table
                                  CONSTRAINT fk_user
                                      FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE,
                                  CONSTRAINT fk_tag
                                      FOREIGN KEY (tag_id)  REFERENCES public.tags(id) ON DELETE CASCADE
);