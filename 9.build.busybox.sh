#!/bin/bash

VER=24

helm delete my-busybox --purge
kubectl delete pods,svc,statefulsets,pvc --all --namespace=busybox
kubectl delete secret gsec
pushd ~/git_madcore/plugins/busybox
  helm package . --version 0.1.$VER
popd
helm install --name my-busybox local/busybox --wait --debug
#kubectl get pods,svc,statefulsets,pvc -o wide --namespace=busybox
#kubectl --namespace=busybox exec -it my-busybox-busybox-0 -- /bin/bash
#kubectl describe statefulset my-busybox-busybox --namespace=busybox
kubectl describe pod my-busybox-busybox-0 --namespace=busybox
