package com.emre.utility;


import java.util.List;
import java.util.Optional;

/*<T>   Enittty adı Type
<ID> enititynin @Id ile işaretlenmiş alanın dataType i
*
* */
public interface IService <T,ID> {

    T save (T t); // tekil kaydetme metodu

    /*belli bir entity listesini kaydetmek için kullanıırz
    kayıt işleminden sonra kaydedilen tüm kayıtları almış oldukları
    id ler ile listesini geri döneriz.*/
    Iterable<T> saveAll(Iterable<T> entities);

    T update (T t);

    void delete (T t);

    void deleteById(ID id);

    Optional<T> findById(ID id);

    List<T> findAll();



}
