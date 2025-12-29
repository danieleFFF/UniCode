ALTER TABLE public.exercises ADD COLUMN points INTEGER DEFAULT 0;

UPDATE public.exercises SET points = 10 WHERE difficulty = 'Easy';
UPDATE public.exercises SET points = 50 WHERE difficulty = 'Medium';
UPDATE public.exercises SET points = 100 WHERE difficulty = 'Hard';
