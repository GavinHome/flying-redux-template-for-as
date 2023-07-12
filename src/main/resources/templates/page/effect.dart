import 'package:flutter/material.dart' hide Action, Page;
import 'package:flying_redux/flying_redux.dart';

import 'action.dart';
import 'state.dart';

buildEffect() {
  return combineEffects<$nameState>(<Object, Effect<$nameState>>{
    Lifecycle.initState: _onInit,
    $nameAction.onSomeAction: _onSomeAction,
  });
}

void _onInit(Action action, ComponentContext<$nameState> ctx) {
  ctx.dispatch($nameActionCreator.initAction([]));
}

void _onSomeAction(Action action, ComponentContext<$nameState> ctx) {
  Navigator.of(ctx.context)
      .pushNamed('some_route_name', arguments: null)
      .then((dynamic toDo) {
    if (toDo != null) {
      ctx.dispatch($nameActionCreator.someAction(toDo));
    }
  });
}
