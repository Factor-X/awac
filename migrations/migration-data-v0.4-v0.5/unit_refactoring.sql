BEGIN TRANSACTION READ WRITE;

DO $$

DECLARE oldCategoryId integer;
DECLARE oldUnitId integer;
DECLARE newCategoryId integer;
DECLARE newUnitId integer;

BEGIN

  -- 1) update 'ref' field of unit & unit category tables

  update unit set ref=('U' || ref);
  update unit_category set ref=('UC' || ref);


  -- 2) replace 'hour' unit (from 'Time' category) by 'h' unit (from 'Duration' category), then delete 'hour 'unit and 'Time' category

  select id from unit_category where name = 'Time' into oldCategoryId;
  select id from unit where symbol = 'heure' and category_id = oldCategoryId into oldUnitId;

  select id from unit_category where name = 'Duration' into newCategoryId;
  select id from unit where symbol = 'h' and category_id = newCategoryId into newUnitId;

  update doublequestion set unitcategory_id = newCategoryId where unitcategory_id = oldCategoryId;
  update answer_value set longdata = newUnitId where longdata = oldUnitId and id in (select id from doubleanswervalue);

  update unit set category_id = null where id = oldUnitId;
  update unit_category set mainunit_id = null where id = oldCategoryId;
  delete from unit where id = oldUnitId;
  delete from unit_category where id = oldCategoryId;

END $$;

COMMIT;