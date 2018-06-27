#sql("findByCodes")
  select s.* from s_role_ser rs left join s_ser s on rs.serCode=s.code where s.dAt is  null and s.enabled='0'
  #if(cond.length??0>0)
  and rs.roleCode in
  (#for(x:cond)
    #if(for.first)
      '#(x)'
    #else
      ,'#(x)'
    #end
  #end)
  #end
#end