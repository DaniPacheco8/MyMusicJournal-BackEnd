-- Add rating column to journal_entries table
ALTER TABLE journal_entries ADD COLUMN IF NOT EXISTS rating INTEGER;

-- Add background_image column to journal_entries table
ALTER TABLE journal_entries ADD COLUMN IF NOT EXISTS background_image TEXT;

-- Add latitude and longitude columns to concerts table
ALTER TABLE concerts ADD COLUMN IF NOT EXISTS latitude DOUBLE PRECISION;
ALTER TABLE concerts ADD COLUMN IF NOT EXISTS longitude DOUBLE PRECISION;

-- Remove rating, notes, and background_image columns from concerts table (if they exist)
ALTER TABLE concerts DROP COLUMN IF EXISTS rating;
ALTER TABLE concerts DROP COLUMN IF EXISTS notes;
ALTER TABLE concerts DROP COLUMN IF EXISTS background_image;
