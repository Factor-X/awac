DO $$

DECLARE
  oldIds bigint[];
  oldId bigint;
  newId bigint;

BEGIN

  RAISE NOTICE 'drop (permanently) fk-constraints on ''scope'' table';

  ALTER TABLE scope DROP CONSTRAINT "fk_q0pdnniqpy1x4ocjyb0650qh2";
  ALTER TABLE scope DROP CONSTRAINT "fk_bjury37ja7bpp0ukgb9xkhlpf";
  ALTER TABLE scope DROP CONSTRAINT "fk_taciekg87eqhaup5v9os4yxl3";


  RAISE NOTICE 'update organizations ids (--> set organization.id = organization.scope.id)';

  -- drop (temporarily) organization_id fk-constraints on ''account'' and ''site'' tables', to allow cascade update
  ALTER TABLE account DROP CONSTRAINT "fk_q2lwr1lu98bs4ix6doelxpcok";
  ALTER TABLE site DROP CONSTRAINT "fk_gey7voiyakoqmd39ox62chdt7";

  -- set organization id = scope id + 1000 (temporarily, to avoid problems of primary key)
  oldIds:=array(SELECT id FROM organization);
  FOREACH oldId IN ARRAY oldIds LOOP
  	-- get new organization id (scope id + 1000)
    SELECT id+1000 FROM scope where organization_id = oldId and type = '1' into newId;
    -- update organization id (and cascade update on sites and accounts)
    UPDATE organization SET id = newId where id = oldId;
    UPDATE site SET organization_id = newId where organization_id = oldId;
    UPDATE account SET organization_id = newId where organization_id = oldId;
  END LOOP;
 
 -- set organization id = scope id
  oldIds:=array(SELECT id FROM organization);
  FOREACH oldId IN ARRAY oldIds LOOP
  	-- get new organization id (organization id - 1000)
    newId:= oldId-1000;
    -- update organization id (and cascade update on sites and accounts)
    UPDATE organization SET id = newId where id = oldId;
    UPDATE site SET organization_id = newId where organization_id = oldId;
    UPDATE account SET organization_id = newId where organization_id = oldId;
  END LOOP;

  -- restore organization_id fk-constraints on ''account'' and ''site'' tables'
  ALTER TABLE account
    ADD CONSTRAINT fk_account_organization_id FOREIGN KEY (organization_id)
        REFERENCES organization (id) MATCH SIMPLE
        ON UPDATE CASCADE ON DELETE CASCADE;

  ALTER TABLE site
    ADD CONSTRAINT fk_site_organization_id FOREIGN KEY (organization_id)
        REFERENCES organization (id) MATCH SIMPLE
        ON UPDATE CASCADE ON DELETE CASCADE;


  RAISE NOTICE 'update sites ids (--> set site.id = site.scope.id)';

  -- set site id = scope id + 1000 (temporarily, to avoid problems of primary key)
  oldIds:=array(SELECT id FROM site);
  FOREACH oldId IN ARRAY oldIds LOOP
    SELECT id+1000 FROM scope where site_id = oldId and type = '2' into newId;
    UPDATE site SET id = newId where id = oldId;
   END LOOP;

  -- set site id = scope id
  oldIds:=array(SELECT id FROM site);
  FOREACH oldId IN ARRAY oldIds LOOP
    newId:= oldId-1000;
    UPDATE site SET id = newId where id = oldId;
  END LOOP;

  
  RAISE NOTICE 'create new scope fk-constraints on ''organization'', ''site'' & ''product'' tables';

  ALTER TABLE organization
    ADD CONSTRAINT fk_organization_scope_id FOREIGN KEY (id)
        REFERENCES scope (id) MATCH SIMPLE
        ON UPDATE CASCADE ON DELETE CASCADE;
 
  ALTER TABLE site
    ADD CONSTRAINT fk_site_scope_id FOREIGN KEY (id)
        REFERENCES scope (id) MATCH SIMPLE
        ON UPDATE CASCADE ON DELETE CASCADE;
 
  ALTER TABLE product
    ADD CONSTRAINT fk_product_scope_id FOREIGN KEY (id)
        REFERENCES scope (id) MATCH SIMPLE
        ON UPDATE CASCADE ON DELETE CASCADE;


  RAISE NOTICE 'delete ''organization_id'', ''site_id'', ''product_id'' and ''type'' columns on ''scope'' table';

  ALTER TABLE scope
  	DROP COLUMN organization_id,
  	DROP COLUMN site_id,
  	DROP COLUMN product_id,
  	DROP COLUMN type;


  RAISE NOTICE 'delete technical segment columns on ''organization'', ''site'' & ''product'' tables';

  ALTER TABLE organization
  	DROP COLUMN creationdate,
  	DROP COLUMN creationuser,
  	DROP COLUMN lastupdatedate,
  	DROP COLUMN lastupdateuser;

  ALTER TABLE site
  	DROP COLUMN creationdate,
  	DROP COLUMN creationuser,
  	DROP COLUMN lastupdatedate,
  	DROP COLUMN lastupdateuser;

  ALTER TABLE product
  	DROP COLUMN creationdate,
  	DROP COLUMN creationuser,
  	DROP COLUMN lastupdatedate,
  	DROP COLUMN lastupdateuser;

 
END $$;
