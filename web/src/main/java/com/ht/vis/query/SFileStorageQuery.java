package com.ht.vis.query;
import cn.hutool.core.util.StrUtil;
import com.ht.vis.kits.DateKit;
import com.ht.vis.core.CoreQuery;
/**
 * Generated by ap-jf.
 */
@SuppressWarnings("serial")
public class SFileStorageQuery extends CoreQuery {

        private String target;
        public void setTarget(String target){
                this.target=target;
        }
        public String getTarget(){
            return this.target;
        }
        private String type;
        public void setType(String type){
                this.type=type;
        }
        public String getType(){
            return this.type;
        }
        private String name;
        public void setName(String name){
                this.name=name;
        }
        public String getName(){
            return this.name;
        }
        private String code;
        public void setCode(String code){
                this.code=code;
        }
        public String getCode(){
            return this.code;
        }


}