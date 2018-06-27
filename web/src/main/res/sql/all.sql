#namespace("resSql")
  #include("res.sql")
#end
#namespace("serSql")
  #include("ser.sql")
#end

#sql("queryByAndCond")
  select * from #(table) where 1=1
    #for(x : cond)
        #if(x.key=='orderBy')
          #set(orderby='order by '+x.value)
        #elseif(x.key=='dAt')
          #set(dAt='and dAt is null')
        #else
          and #(x.key)   #para(x.value)
        #end
    #end
    #(dAt) #if(isNotBlank(orderby))#(orderby)#else order by id desc#end
#end

#sql("queryFirstByAndCond")
  select * from #(table) where 1=1
    #for(x : cond)
        #if(x.key=='orderBy')
          #set(orderby='order by '+x.value)
        #elseif(x.key=='dAt')
          #set(dAt='and dAt is null')
        #else
          and #(x.key)   #para(x.value)
        #end
    #end
    #(dAt) #if(isNotBlank(orderby))#(orderby)#else order by id desc  #end limit 1
#end

#sql("queryColumnComment")
  select column_comment from INFORMATION_SCHEMA.Columns where table_name='#(tableName)' and table_schema='#(tableSchema)' and column_name='#(columnName)'
#end

#sql("queryTableComment")
  select table_comment from INFORMATION_SCHEMA.Tables where table_name='#(tableName)' and table_schema='#(tableSchema)'
#end
