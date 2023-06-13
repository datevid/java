Validar si estás dentro de una transacción:

```java
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Override
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public ExpedienteResponse registroExpedienteBack2(BeanRequest beanRequest) {
    boolean isInTransaction = TransactionSynchronizationManager.isActualTransactionActive();
    if (isInTransaction) {
        System.out.println("Estás dentro de una transacción.");
    } else {
        System.out.println("No estás dentro de una transacción.");
    }
    return null;
}
```
