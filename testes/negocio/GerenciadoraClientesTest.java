package negocio;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * ALUNO:
 * DATA:
 */
public class GerenciadoraClientesTest {
    private GerenciadoraClientes gerClientes;
    private int idCliente01 = 1;
    private int idCliente02 = 2;

    @Before
    public void setup() {
        Cliente cliente01 = new Cliente(idCliente01, "João", 31, "joao@gmail.com", 1, true);
        Cliente cliente02 = new Cliente(idCliente02, "Maria", 34, "maria@gmail.com", 1, true);

        ArrayList<Cliente> clientesDoBanco = new ArrayList<>();
        clientesDoBanco.add(cliente01);
        clientesDoBanco.add(cliente02);
        gerClientes = new GerenciadoraClientes(clientesDoBanco);
    }

    @After
    public void tearDown() {
        gerClientes.limpa();
    }

    @Test
    public void testPesquisaCliente() {
        Cliente cliente = gerClientes.pesquisaCliente(idCliente01);
        assertThat(cliente.getId(), is(idCliente01));
    }

    @Test
    public void testRemoveCliente() {
        boolean clienteRemovido = gerClientes.removeCliente(idCliente02);
        assertThat(clienteRemovido, is(true));
        assertThat(gerClientes.getClientesDoBanco().size(), is(1));
        assertNull(gerClientes.pesquisaCliente(idCliente02));
    }

    public boolean validaIdade(Cliente cliente) throws IdadeNaoPermitidaException {
        int idade = cliente.getIdade();
    	if(idade < 18 || idade > 65)
        	throw new IdadeNaoPermitidaException(IdadeNaoPermitidaException.MSG_IDADE_INVALIDA);
        return true;
    }
    
    @Test
    public void testClienteIdadeAceitavel() throws IdadeNaoPermitidaException {
        Cliente cliente = new Cliente(1, "Pedro Lima", 25, "pedrolima@gmail.com", 1, true);
        boolean idadeValida = gerClientes.validaIdade(cliente);
        assertTrue(idadeValida);
    }


    @Test
    public void testIdadeInvalidaAbaixo() throws IdadeNaoPermitidaException {
        Cliente cliente = new Cliente(4, "Ana", 17, "ana@gmail.com", 1, true);
        assertFalse(gerClientes.validaIdade(cliente));
    }

    @Test
    public void testIdadeInvalidaAcima() throws IdadeNaoPermitidaException {
        Cliente cliente = new Cliente(5, "Carlos", 66, "carlos@gmail.com", 1, true);
        assertFalse(gerClientes.validaIdade(cliente));
    }

    @Test
    public void testIdadeLimiteInferior() throws IdadeNaoPermitidaException {
        Cliente cliente = new Cliente(6, "Lucas", 18, "lucas@gmail.com", 1, true);
        assertTrue(gerClientes.validaIdade(cliente));
    }

    @Test
    public void testIdadeLimiteSuperior() throws IdadeNaoPermitidaException {
        Cliente cliente = new Cliente(7, "Mariana", 99999, "mariana@gmail.com", 1, true);
        boolean idadeValida = gerClientes.validaIdade(cliente);
        assertTrue(idadeValida);
    }
}
