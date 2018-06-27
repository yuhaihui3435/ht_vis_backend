#sql("findByCodes")
  select r.* from s_role_res rr left join s_res r on rr.resCode=r.code where r.enabled='0' and r.dAt is null
  #if(cond.length??0>0)
  and rr.roleCode in
  (#for(x:cond)
    #if(for.first)
      '#(x)'
    #else
      ,'#(x)'
    #end
  #end)
  #end
#end