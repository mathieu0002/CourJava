INSERT INTO biblio.auteur ( nom, prenom, nationalite, date_naissance, date_deces) VALUES
( 'Hugo', 'Victor', 'Français', '1802-02-26', '1885-05-22'),
( 'Borges', 'Jorge Luis', 'Argentin', '1899-11-07', '1986-01-04'),
( 'Diderot', 'Denis.', 'Français', '1718-08-31', '1783-07-31');


INSERT INTO biblio.livre ( titre, nb_pages, editeur, date_publication, created_at, updated_at, auteur_id) VALUES
( 'Les Misérables', 1232, 'A. Lacroix', '1862-01-01', NOW(), NOW(), 1),
( 'Fictions', 224, 'Sur', '1944-01-01', NOW(), NOW(), 2),
( 'Jacques le fataliste', 384, 'Garnier', '1796-01-01', NOW(), NOW(), 3);

