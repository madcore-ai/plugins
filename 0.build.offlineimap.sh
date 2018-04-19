#!/bin/bash

VER=33

helm delete my-offlineimap --purge
kubectl delete pods,svc,statefulsets,pvc --all --namespace=offlineimap
kubectl delete secret gsec --namespace=offlineimap
pushd ~/git_madcore/plugins/offlineimap
  helm package . --version 0.1.$VER
popd
helm install --name my-offlineimap local/offlineimap --wait --debug
#kubectl get pods,svc,statefulsets,pvc -o wide --namespace=offlineimap
#kubectl --namespace=offlineimap exec -it my-offlineimap-offlineimap-0 -- /bin/bash
#kubectl describe statefulset my-offlineimap-offlineimap --namespace=offlineimap
#kubectl describe pod my-offlineimap-offlineimap-0 --namespace=offlineimap
#kubectl logs my-offlineimap-offlineimap-0 --namespace=offlineimap
