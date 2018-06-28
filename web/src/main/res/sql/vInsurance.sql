#sql("findByLicensePlateInDate")
  SELECT * FROM v_insurance WHERE licensePlate=#para(licensePlate)
  AND (
    (bAt<=#para(bAt) AND eAt>=#para(bAt)) OR (bAt<=#para(eAt) AND eAt>=#para(eAt))
  )
  AND dAt is NULL
#end

#sql("findByLicensePlateInDateNeId")
  SELECT * FROM v_insurance WHERE licensePlate=#para(licensePlate)
  AND (
    (bAt<=#para(bAt) AND eAt>=#para(bAt)) OR (bAt<=#para(eAt) AND eAt>=#para(eAt))
  )
  AND dAt is NULL AND id!=#para(id)
#end