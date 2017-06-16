#!/bin/bash

VER=4

helm delete my-kfn --purge
kubectl delete pods,svc,statefulsets,pvc --all --namespace=social
kubectl delete secret gsec
pushd ~/projects/python/madcore/plugins/kfn
  helm package . --version 0.1.$VER
popd
helm install --name my-kfn local/kfn --wait --debug
kubectl get pods,svc,statefulsets,pvc -o wide --namespace=social
kubectl --namespace=social exec -it my-kfn-kfn-0 -- /bin/bash
kubectl describe statefulset my-kfn-kfn --namespace=social
kubectl describe pod my-kfn-kfn-0 --namespace=social
