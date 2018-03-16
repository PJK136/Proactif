/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Intervention;

/**
 *
 * @author Azergante
 */
public class InterventionDAO {
    public static void create(Intervention intervention) {
        JpaUtil.getEntityManager().persist(intervention);
    }
}
