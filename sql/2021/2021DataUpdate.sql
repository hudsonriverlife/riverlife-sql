-- Remove duplicate Blue Mussel from Macros
DELETE FROM riverlife.macroinvertebrate WHERE macroinvertebrate_id = 61;

-- Clean up duplicate barnacle from macros
UPDATE riverlife.site_sampling_macroinvertebrate_count SET macroinvertebrate_id = 26 WHERE macroinvertebrate_id = 62;
DELETE FROM riverlife.macroinvertebrate WHERE macroinvertebrate_id = 62;

-- Remove nonspecified crabs from macro
DELETE FROM riverlife.macroinvertebrate WHERE macroinvertebrate_id = 52;
DELETE FROM riverlife.site_sampling_macroinvertebrate_count WHERE macroinvertebrate_id = 52;

-- Clean up duplicate young of year from fish
UPDATE riverlife.site_sampling_fish_count SET fish_id = 63 WHERE fish_id = 66;
DELETE FROM riverlife.fish WHERE fish_id = 66;