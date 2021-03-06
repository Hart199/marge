package net.cordaclub.marge.insurer

import io.bluebank.braid.core.async.getOrThrow
import io.cordite.dgl.corda.account.GetAccountFlow
import io.cordite.dgl.corda.impl.LedgerApiImpl
import io.vertx.core.Future
import net.corda.core.node.AppServiceHub
import net.corda.core.utilities.getOrThrow
import net.cordaclub.marge.Initializer

class InsurerAPI(private val serviceHub: AppServiceHub) : Initializer(){
    companion object {
        const val INSURER_ACCOUNT = "insurer"
    }

    override fun initialiseDemo() : Future<Unit> {
        if (!initialised) {
            val notary = serviceHub.networkMapCache.notaryIdentities.first()
            try {
                serviceHub.startFlow(GetAccountFlow(accountId = INSURER_ACCOUNT)).returnValue.getOrThrow()
            } catch (e: Exception) {
                val ledgerApi = LedgerApiImpl(serviceHub)
                val account = ledgerApi.createAccount(INSURER_ACCOUNT, notary.name).getOrThrow()
            }
        }
        initialised = true
        return Future.succeededFuture()
    }
}

