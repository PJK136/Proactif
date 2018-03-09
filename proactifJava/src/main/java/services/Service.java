/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.JpaUtil;
import dao.PersonDAO;
import entities.Person;
import javax.persistence.RollbackException;

/**
 *
 * @author tcadet
 */
public class Service {
    
    public static void inscription(Person p)
    {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        PersonDAO.create(p);
        try 
        {
            JpaUtil.validerTransaction();            
        } 
        catch(RollbackException e) 
        {
            JpaUtil.annulerTransaction(); 
        }
        finally
        {
            JpaUtil.fermerEntityManager();
        }
        
    }        
}
