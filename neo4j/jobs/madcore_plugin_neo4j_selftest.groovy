job('madcore.plugin.neo4j.selftest') {
  parameters {
    stringParam('APP_NAME', 'neo4j', '')
  }
    steps {
        def command = """#!/bin/bash

kubectl exec neo4j-core-0 --namespace=neo4j-cluster -- bin/cypher-shell "CALL dbms.cluster.overview()"
"""
        shell(command)
    }
}
