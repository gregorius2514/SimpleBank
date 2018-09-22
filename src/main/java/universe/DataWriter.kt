package universe

interface DataWriter<T> {

    //FIXME Change names
    fun writeData(record : T)
    //FIXME change names and throw custom exception
    fun overrideAllData(records : List<T>)
}