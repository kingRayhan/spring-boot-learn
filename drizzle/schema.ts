import {
  date,
  integer,
  pgTable,
  timestamp,
  uuid,
  varchar,
} from "drizzle-orm/pg-core";

const dateProps = {
  createdAt: timestamp("created_at"),
  updatedAt: timestamp("updated_at"),
};

export const usersTable = pgTable("users", {
  id: uuid("id").defaultRandom().primaryKey(),
  name: varchar({ length: 255 }).notNull(),
  email: varchar({ length: 255 }).notNull(),
  password: varchar({ length: 255 }).notNull(),
  ...dateProps,
});

export const addressesTable = pgTable("addresses", {
  id: uuid("id").defaultRandom().primaryKey(),
  street: varchar({ length: 255 }).notNull(),
  city: varchar({ length: 255 }).notNull(),
  zip: varchar({ length: 10 }).notNull(),
  userId: uuid("user_id")
    .notNull()
    .references(() => usersTable.id, { onDelete: "cascade" }),
  ...dateProps,
});

export const profilesTable = pgTable("profiles", {
  id: uuid("id").defaultRandom().primaryKey(),
  bio: varchar({ length: 255 }),
  date_of_birth: date("date_of_birth"),
  phone_number: varchar({ length: 255 }),
  loyalty_points: integer().default(0),
  userId: uuid("user_id")
    .notNull()
    .references(() => usersTable.id, { onDelete: "cascade" }),
  ...dateProps,
});

export const tagsTable = pgTable("tags", {
  id: uuid("id").defaultRandom().primaryKey(),
  name: varchar({ length: 255 }).notNull(),
  description: varchar({ length: 255 }),
  ...dateProps,
});

export const userTagsTable = pgTable("user_tags", {
  id: uuid("id").defaultRandom().primaryKey(),
  userId: uuid("user_id")
    .notNull()
    .references(() => usersTable.id, { onDelete: "cascade" }),
  tagId: uuid("tag_id")
    .notNull()
    .references(() => tagsTable.id, { onDelete: "cascade" }),
});

export const categoriesTable = pgTable("categories", {
  id: uuid("id").defaultRandom().primaryKey(),
  name: varchar({ length: 255 }).notNull(),
  ...dateProps,
});

export const productsTable = pgTable("products", {
  id: uuid("id").defaultRandom().primaryKey(),
  name: varchar({ length: 255 }).notNull(),
  description: varchar({ length: 255 }),
  price: integer().notNull(),
  categoryId: uuid("category_id")
    .notNull()
    .references(() => categoriesTable.id, { onDelete: "restrict" }),
  ...dateProps,
});

export const wishlistsTable = pgTable("wishlists", {
  id: uuid("id").defaultRandom().primaryKey(),
  userId: uuid("user_id")
    .notNull()
    .references(() => usersTable.id, { onDelete: "cascade" }),
  productId: uuid("product_id")
    .notNull()
    .references(() => productsTable.id, { onDelete: "cascade" }),
  ...dateProps,
});
