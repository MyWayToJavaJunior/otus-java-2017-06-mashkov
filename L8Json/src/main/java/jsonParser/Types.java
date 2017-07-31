package jsonParser;


public enum  Types {
    INTEGER(Integer.class), STRING(String.class);

    Class klass;

    Types(Class klass){
        this.klass = klass;
    }
    Class getKlass(){
        return klass;
    }
}
