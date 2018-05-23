/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.dao.*;
import modelo.vo.*;
import vista.*;

public class ContPrincipal implements ActionListener {
    
  private VistaPrincipal vistaprincipal;
  
  
  public ContPrincipal (VistaPrincipal vistaprincipal ){
      
      this.vistaprincipal=vistaprincipal;
      this.vistaprincipal.jMenuItemAgencia.addActionListener(this);
      this.vistaprincipal.jMenuItemAutomovil.addActionListener(this);
      this.vistaprincipal.jMenuItemCliente.addActionListener(this);
      this.vistaprincipal.jMenuItemGaraje.addActionListener(this);
      this.vistaprincipal.jMenuItemReserva.addActionListener(this);
      this.vistaprincipal.jMenuItemReservas.addActionListener(this);
      this.vistaprincipal.jMenuItemsalir.addActionListener(this);
      this.vistaprincipal.setVisible(true);
   
      
      
    }
  

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource().equals(this.vistaprincipal.jMenuItemAgencia)){
           ContAgencia agencia=new ContAgencia(new VistaAgencia(),new Agencia_dao() );
           this.vistaprincipal.dispose();
       }
         if (ae.getSource().equals(this.vistaprincipal.jMenuItemAutomovil)){
           ContAutomovil automovil=new ContAutomovil(new VistaAutomovil(),new Automovil_dao(),new Garaje_dao());
           this.vistaprincipal.dispose();
       }
          if (ae.getSource().equals(this.vistaprincipal.jMenuItemCliente)){
           ContCliente cliente=new ContCliente(new VistaCliente(),new Cliente_dao() );
           this.vistaprincipal.dispose();
       }
           if (ae.getSource().equals(this.vistaprincipal.jMenuItemGaraje)){
           ContGaraje garaje=new ContGaraje(new VistaGaraje(),new Garaje_dao() );
           this.vistaprincipal.dispose();
       }
            if (ae.getSource().equals(this.vistaprincipal.jMenuItemReserva)){
           ContReserva reserva=new ContReserva(new VistaReserva(),new Reserva_dao(),new Agencia_dao(), new Automovil_dao(), new Cliente_dao());
           this.vistaprincipal.dispose();
       }
             if (ae.getSource().equals(this.vistaprincipal.jMenuItemReservas)){
           
                 ContConsulta consulta=new ContConsulta(new VistaConsulta(),new Reserva_dao());
           this.vistaprincipal.dispose();
       }
              if (ae.getSource().equals(this.vistaprincipal.jMenuItemsalir)){
              
              System.exit(0);
       }
        
               
        
        
    }

  
  
}

