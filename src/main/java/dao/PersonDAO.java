/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Person;

/**
 *
 * @author tcadet
 */
public class PersonDAO {
    
    public static void create(Person p)
    {
        JpaUtil.obtenirEntityManager().persist(p);
    }        
}
