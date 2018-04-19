job('madcore.plugin.social.process.facebook') {
    parameters {
	    stringParam('RELEASE_NAME', 'social', '')
    }

    steps {
        def command = """#!/bin/bash

kubectl exec zk-\$RELEASE_NAME-2 --namespace=kafka -it -- zkServer.sh status
kubectl exec zk-\$RELEASE_NAME-2 --namespace=kafka -it -- zkServer.sh status
kubectl exec zk-\$RELEASE_NAME-2 --namespace=kafka -it -- zkServer.sh status

"""
        shell(command)
    }
}
