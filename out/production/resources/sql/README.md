# Database Migration: Rename dayTime column to day_time

This directory contains SQL scripts for database migrations.

## rename_daytime_column.sql

This script renames the "dayTime" column to "day_time" in the appointments table.

### How to apply

Run the SQL script against your PostgreSQL database:

```bash
psql -U postgres -d database -f rename_daytime_column.sql
```

Replace `postgres` with your database username and `database` with your database name if they are different.

### Changes made

1. Modified the `@Column` annotation in `Appointments.java` to use the new column name "day_time".
2. Created this SQL script to rename the column in the database.

The Java code and database schema should now be in sync with the column name "day_time".